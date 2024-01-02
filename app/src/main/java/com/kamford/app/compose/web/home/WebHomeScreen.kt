package com.kamford.app.compose.web.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.R
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.articles.Article
import com.kamford.app.data.local.courses.Course
import com.kamford.app.data.local.products.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebHomeScreen(
    openDrawer: () -> Unit,
    onProductClick: (Product) -> Unit,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val viewModel: WebHomeViewModel = viewModel(
        key = "web_home",
        factory = viewModelProviderFactoryOf { WebHomeViewModel() }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WebHomeTopAppBar(
                scrollBehavior = scrollBehavior,
                openDrawer = openDrawer
            )
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            state = state
        ) {

            if (viewState.highlightedArticle != null ) {
                item { PostListTopSection(viewState.highlightedArticle!!,onArticleClick) }
            }
            if (viewState.recommendedArticles != null) {
                item {
                    PostListSimpleSection(
                        viewState.recommendedArticles!!,onArticleClick
                    )
                }
            }
            if (viewState.popularProducts != null) {
                item {
                    PostListPopularSection(
                        viewState.popularProducts!!,onProductClick
                    )
                }
            }
            if (viewState.recentArticles != null) {
                item { PostListHistorySection(viewState.recentArticles!!,onArticleClick) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WebHomeTopAppBar(
    openDrawer: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        title = {
            Row {
                Icon(
                    painterResource(R.drawable.ic_kamford_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column{
                    Text(
                        text = "金科偉業",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Kamford.App",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Default.MenuOpen,
                    contentDescription = stringResource(id = R.string.cd_open_navigation_drawer)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "Search is not yet implemented in this configuration",
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.cd_search)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
private fun PostListTopSection(post: Article,onArticleClick: (Article) -> Unit) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.home_top_section_title),
        style = MaterialTheme.typography.titleMedium
    )
    PostCardTop(
        post = post,
        modifier = Modifier.clickable(onClick = { onArticleClick(post) })
    )
    PostListDivider()
}


@Composable
private fun PostListSimpleSection(
    posts: List<Article>,onArticleClick: (Article) -> Unit
) {
    Column {
        posts.forEach { post ->
            PostCardSimple(
                post = post,
                onArticleClick = onArticleClick
            )
            PostListDivider()
        }
    }
}


@Composable
private fun PostListPopularSection(
    posts: List<Product>,
    onProductClick: (Product) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.home_popular_section_title),
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (post in posts) {
                PostCardPopular(
                    post,onProductClick
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        PostListDivider()
    }
}

@Composable
private fun PostListHistorySection(
    posts: List<Article>,onArticleClick: (Article) -> Unit
) {
    Column {
        posts.forEach { post ->
            PostCardHistory(post,onArticleClick)
            PostListDivider()
        }
    }
}


@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}