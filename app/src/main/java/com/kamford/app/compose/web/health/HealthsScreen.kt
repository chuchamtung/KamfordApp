package com.kamford.app.compose.web.health

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.R
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.KamfordSnackbarHost
import com.kamford.app.compose.utils.ListTopAppBar
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.compose.web.news.ArticleAuthorAndReadTime
import com.kamford.app.compose.web.news.ArticleItem
import com.kamford.app.compose.web.news.ArticleListDivider
import com.kamford.app.data.local.articles.Article
import com.kamford.app.di.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthsScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onArticleClick: (Article) -> Unit,
    state: LazyListState = rememberLazyListState(),
    snackbarHostState: SnackbarHostState
) {
    val viewModel: HealthsViewModel = viewModel(
        key = "web_healths",
        factory = viewModelProviderFactoryOf { HealthsViewModel() }
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
                                text = stringResource(id = R.string.home_healths_order_by_time),
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
                                    model = article.image_l_url ?: Constants.IMAGE_BASE_URL+ Constants.IMAGE_SIZE_BIG+ "304126f2-556f-4574-8c01-d00b20ae943b",
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