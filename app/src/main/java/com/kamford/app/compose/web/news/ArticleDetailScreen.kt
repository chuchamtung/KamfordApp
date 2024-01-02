package com.kamford.app.compose.web.news

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.R
import com.kamford.app.compose.utils.BookmarkButton
import com.kamford.app.compose.utils.DetailTopAppBar
import com.kamford.app.compose.utils.FavoriteButton
import com.kamford.app.compose.utils.HtmlToParagraphs
import com.kamford.app.compose.utils.ParagraphToScreen
import com.kamford.app.compose.utils.ShareButton
import com.kamford.app.compose.utils.TextSettingsButton
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.articles.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    articleId: String?,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    state: LazyListState = rememberLazyListState(),
) {
    val viewModel: ArticleDetailViewModel = viewModel(
        key = "web_article_detail_${articleId}",
        factory = viewModelProviderFactoryOf { ArticleDetailViewModel(articleId) }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val selectedArticle_ = viewState.selectedArticle
    val articleCourse = viewState.articleCourse

    var showUnimplementedActionDialog by rememberSaveable { mutableStateOf(false) }
    if (showUnimplementedActionDialog) {
        FunctionalityNotAvailablePopup { showUnimplementedActionDialog = false }
    }

    Row(modifier.fillMaxSize()) {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                DetailTopAppBar(
                    title = articleCourse?.name?:"Course name",
                    isFullScreen = true,
                    onBackPressed = navigateBack
                )
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        FavoriteButton(onClick = { showUnimplementedActionDialog = true })
                        BookmarkButton(isBookmarked = false, onClick = { showUnimplementedActionDialog = true })
                        if (selectedArticle_!= null){
                            ShareButton(onClick = { sharePost(selectedArticle_, context) })
                        }
                        TextSettingsButton(onClick = { showUnimplementedActionDialog = true })
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding),
                state = state
            ) {
                val paragraphList = HtmlToParagraphs(selectedArticle_?.description ?: "<p>Article Description</p>", selectedArticle_?.id ?: "article-id")
                items(paragraphList) {
                    ParagraphToScreen(paragraphs = it)
                }

            }
        }
    }

}

@Composable
private fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = stringResource(id = R.string.article_functionality_not_available),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    )
}

private fun sharePost(article: Article, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, article.name ?: "article title")
        putExtra(Intent.EXTRA_TEXT, article.web_article_detail_url)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.article_share_post)
        )
    )
}