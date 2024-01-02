package com.kamford.app.compose.web

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamford.app.compose.OfflineDialog
import com.kamford.app.compose.web.about.AboutUsScreen
import com.kamford.app.compose.web.about.AlbumDetailScreen
import com.kamford.app.compose.web.health.HealthsScreen
import com.kamford.app.compose.web.home.WebHomeScreen
import com.kamford.app.compose.web.news.ArticleDetailScreen
import com.kamford.app.compose.web.news.NewsScreen
import com.kamford.app.compose.web.product.ProductDetailScreen
import com.kamford.app.compose.web.product.ProductsScreen
import com.kamford.app.compose.web.service.ServicesScreen

@Composable
fun KamfordWebApp(
    openDrawer: () -> Unit,
    startRoute: String,
    modifier: Modifier = Modifier,
    appState: KamfordWebAppState = rememberKamfordWebAppState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    if (appState.isOnline) {
        NavHost(
            modifier = modifier,
            navController = appState.navController,
            startDestination = startRoute
        ) {
            composable(WebScreen.WebHome.route) { navBackStackEntry ->
                WebHomeScreen(
                    openDrawer = openDrawer,
                    onProductClick = {
                        appState.navigateToProductDetail(it.id, navBackStackEntry)
                    },
                    onArticleClick = {
                        appState.navigateToArticleDetail(it.id, navBackStackEntry)
                    }

                )
            }

            composable(WebScreen.WebAboutUs.route) { navBackStackEntry ->
                AboutUsScreen(
                    openDrawer = openDrawer,
                    onAlbumClick = {
                        appState.navigateToAlbumDetail(it.id, navBackStackEntry)
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            composable(WebScreen.WebNews.route) { navBackStackEntry ->
                NewsScreen(
                    openDrawer = openDrawer,
                    onArticleClick = {
                        appState.navigateToArticleDetail(it.id, navBackStackEntry)
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            composable(WebScreen.WebProducts.route) { navBackStackEntry ->
                ProductsScreen(
                    openDrawer = openDrawer,
                    onProductClick = {
                        appState.navigateToProductDetail(it.id, navBackStackEntry)
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            composable(WebScreen.WebHealths.route) { navBackStackEntry ->
                HealthsScreen(
                    openDrawer = openDrawer,
                    onArticleClick = {
                        appState.navigateToArticleDetail(it.id, navBackStackEntry)
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            composable(WebScreen.WebServices.route) { navBackStackEntry ->
                ServicesScreen(
                    openDrawer = openDrawer,
                    onAlbumClick = {
                        appState.navigateToAlbumDetail(it.id, navBackStackEntry)
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            composable(WebScreen.ArticleDetail.route) {
                ArticleDetailScreen(
                    articleId = it.arguments?.getString("articleId"),
                    navigateBack = appState::navigateBack,
                )
            }
            composable(WebScreen.AlbumDetail.route) {
                AlbumDetailScreen(
                    albumId = it.arguments?.getString("albumId"),
                    navigateBack = appState::navigateBack,
                )
            }
            composable(WebScreen.ProductDetail.route) {
                ProductDetailScreen(
                    productId = it.arguments?.getString("productId"),
                    navigateBack = appState::navigateBack,
                )
            }
        }
    }else{
        OfflineDialog{ appState.refreshOnline() }
    }
}