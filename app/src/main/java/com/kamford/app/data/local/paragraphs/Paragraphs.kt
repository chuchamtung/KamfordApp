package com.kamford.app.data.local.paragraphs



data class Paragraphs(
    var id: String,
    var quoteId: String,
    var paragraphType: ParagraphsType,
    var paragraphText: String,
    var orderSort: Int = 0,
    var markupsList: List<Markups> = emptyList()
)

data class Markups(
    var id: String,
    var paragraphId: String,
    val markupType: MarkupType,
    val start: Int,
    var end: Int,
    var href: String? = null
)

enum class MarkupType {
    Link,
    Code,
    Italic,
    Bold,
}

enum class ParagraphsType {
    Title,
    Caption,
    Header,
    Subhead,
    Text,
    CodeBlock,
    Quote,
    Bullet,
    Image
}