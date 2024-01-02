package com.kamford.app.compose.utils

import android.util.Log
import com.kamford.app.data.local.paragraphs.Paragraphs
import com.kamford.app.data.local.paragraphs.ParagraphsType
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.UUID

fun HtmlImageToText(html: String, quoteId: String): List<Paragraphs> {
    var listImageData = mutableListOf<Paragraphs>()
    try {
        val inputHtml: Document = Jsoup.parse(html)
        val images: Elements = inputHtml.select("img[src]")
        var index = 1

        if(!images.isNullOrEmpty()){
            images.forEach { image ->
                listImageData.add(
                        Paragraphs(
                        id = UUID.randomUUID().toString(),
                        quoteId = quoteId,
                        paragraphText = image.attr("src"),
                        orderSort = index,
                        paragraphType = ParagraphsType.Image
                    )
                )
                index ++
            }
        }
    } catch (e: Exception) {
        Log.d("TAG", "Exception: ${e}")
    }
    return listImageData
}

