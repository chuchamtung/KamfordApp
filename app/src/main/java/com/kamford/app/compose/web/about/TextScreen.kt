package com.kamford.app.compose.web.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kamford.app.compose.utils.HtmlToParagraphs
import com.kamford.app.compose.utils.ParagraphToScreen
import com.kamford.app.data.local.courses.Course


@Composable
fun TextsScreen(
    course: Course,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val paragraphList = HtmlToParagraphs(course.description!!, course.id)

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = modifier,
        state = state,
    ) {
        items(paragraphList) {
            ParagraphToScreen(paragraphs = it)
        }
    }
}

