package com.kamford.app.compose.utils

import android.util.Log
import com.kamford.app.data.local.paragraphs.MarkupType
import com.kamford.app.data.local.paragraphs.Markups
import com.kamford.app.data.local.paragraphs.Paragraphs
import com.kamford.app.data.local.paragraphs.ParagraphsType
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.UUID

fun HtmlToParagraphs(html: String, quoteId: String): List<Paragraphs> {
    val listData = mutableListOf<Paragraphs>()
    try {
        val inputDoc: Document = Jsoup.parse(html)
        var postElements = inputDoc.select("p")
        var index = 1
        postElements.forEach { pHtml ->
            pHtml.childNodes().forEach {
                val pDoc: Document = Jsoup.parse(it.outerHtml())

                val pDocImages: Elements = pDoc.select("[src]")
                if(pDocImages.isNotEmpty()){
                    pDocImages.forEach { img ->
                        listData.add(Paragraphs(
                            id = UUID.randomUUID().toString(),
                            quoteId = quoteId,
                            paragraphText = img.attr("src"),
                            paragraphType = ParagraphsType.Image,
                            orderSort = index
                        ))
                        index ++
                    }
                }



                val pNodesText = pDoc.text()
                if (pNodesText.isNotEmpty() && pNodesText.isNotBlank()){
                    var parRow = Paragraphs(
                        id = UUID.randomUUID().toString(),
                        quoteId = quoteId,
                        paragraphText = pNodesText,
                        paragraphType = ParagraphsType.Text
                    )
                    parRow.orderSort = index


                    val pNodesTextB: Elements = pDoc.select("b")
                    if(pNodesTextB.isNotEmpty()){
                        parRow.markupsList = listOf(Markups(
                            id = UUID.randomUUID().toString(),
                            paragraphId = parRow.id,
                            start = 0,
                            markupType = MarkupType.Bold,
                            end = pNodesText.length
                        ))
                    }

                    val pNodesTextA: Elements = pDoc.select("a")
                    if(pNodesTextA.isNotEmpty()){
                        parRow.markupsList = listOf(Markups(
                            id = UUID.randomUUID().toString(),
                            paragraphId = parRow.id,
                            start = 0,
                            markupType = MarkupType.Link,
                            end = pNodesText.length,
                            href = pNodesTextA.attr("href")
                        ))
                    }

                    listData.add(parRow)
                    index ++
                }


            }
        }


    } catch (e: Exception) {
        Log.d("TAG", "Exception: ${e}")
    }
    return listData
}

