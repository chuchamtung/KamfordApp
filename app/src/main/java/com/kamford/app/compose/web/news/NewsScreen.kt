package com.kamford.app.compose.web.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.R
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.KamfordSnackbarHost
import com.kamford.app.compose.utils.ListTopAppBar
import com.kamford.app.compose.utils.TimeAgoUtil
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.articles.Article
import com.kamford.app.di.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onArticleClick: (Article) -> Unit,
    state: LazyListState = rememberLazyListState(),
    snackbarHostState: SnackbarHostState
) {
    val viewModel: NewsViewModel = viewModel(
        key = "web_news",
        factory = viewModelProviderFactoryOf { NewsViewModel() }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val articleList = viewState.articleList

    Scaffold(
        snackbarHost = { KamfordSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ListTopAppBar(openDrawer = openDrawer)
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier,
            contentPadding = innerPadding,
            state = state
        ) {
            item {
                Column {
                    articleList?.forEachIndexed { index, article ->
                        if (index == 0){
                            Text(
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                                text = stringResource(id = R.string.home_news_order_by_time),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable(onClick = { onArticleClick(article) })
                            ) {
                                val imageModifier = Modifier
                                    .heightIn(min = 180.dp)
                                    .fillMaxWidth()
                                    .clip(shape = MaterialTheme.shapes.medium)

                                ItemGlideImage(
                                    model = article.image_l_url ?: Constants.IMAGE_BASE_URL+ Constants.IMAGE_SIZE_BIG+ "d0a7f1af-5973-41ad-bdf0-64967601ac3b",
                                    contentDescription = null,
                                    modifier = imageModifier,
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.height(16.dp))

                                Text(
                                    text = article.name ?: "name",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                ArticleAuthorAndReadTime(
                                    article = article,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                        }else{
                            ArticleItem(article = article){
                                onArticleClick(article)
                            }
                        }
                        ArticleListDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, onArticleClick: (Article) -> Unit) {
    Row(
        Modifier.clickable(onClick = { onArticleClick(article) })
    ) {
        if (article.imageId != null){
            ArticleImage(
                article = article,
                modifier = Modifier.padding(16.dp)
            )
        }else{
            Icon(
                painterResource(R.drawable.ic_kamford_logo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.01f),
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
        }
        Column(
            Modifier
                .weight(1f)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_news_order_by_time),
                style = MaterialTheme.typography.labelMedium
            )
            ArticleTitle(article = article)
            ArticleAuthorAndReadTime(
                article = article,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        IconButton(onClick = {  }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.cd_more_actions)
            )
        }
    }
}



@Composable
fun ArticleListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun ArticleImage(article: Article, modifier: Modifier = Modifier) {
    ItemGlideImage(
        model = article.image_s_url ,
        contentDescription = null,
        modifier = modifier
            .size(60.dp, 40.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
    )
}


@Composable
fun ArticleTitle(article: Article) {
    Text(
        text = article.name ?: "name",
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}


@Composable
fun ArticleAuthorAndReadTime(
    article: Article,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = stringResource(
                id = R.string.home_post_min_read,
                formatArgs = arrayOf(
                    "Post by ${article.author}",
                    TimeAgoUtil.format(article.createdAt ?: 0L)
                )
            ),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}