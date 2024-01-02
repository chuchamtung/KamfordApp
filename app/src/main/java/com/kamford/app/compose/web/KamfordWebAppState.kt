package com.kamford.app.compose.web

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * List of screens for [KamfordWebApp]
 */
sealed class WebScreen(val route: String) {

    object WebHome : WebScreen("web-home")
    object WebAboutUs : WebScreen("web-about-us")
    object WebNews : WebScreen("web-news")
    object WebProducts : WebScreen("web-products")
    object WebHealths : WebScreen("web-healths")
    object WebServices : WebScreen("web-services")

    object ProductDetail : WebScreen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }
    object ArticleDetail : WebScreen("article/{articleId}") {
        fun createRoute(articleId: String) = "article/$articleId"
    }
    object AlbumDetail : WebScreen("album/{albumId}") {
        fun createRoute(albumId: String) = "album/$albumId"
    }
}

@Composable
fun rememberKamfordWebAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    KamfordWebAppState(navController, context)
}

class KamfordWebAppState(
    val navController: NavHostController,
    private val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    fun refreshOnline() {
        isOnline = checkIfOnline()
    }

    fun navigateToWebHome() {
        navController.navigate(WebScreen.WebHome.route)
    }
    fun navigateToWebAboutUs() {
        navController.navigate(WebScreen.WebAboutUs.route)
    }
    fun navigateToWebNews() {
        navController.navigate(WebScreen.WebNews.route)
    }
    fun navigateToWebProducts() {
        navController.navigate(WebScreen.WebProducts.route)
    }
    fun navigateToWebHealths() {
        navController.navigate(WebScreen.WebHealths.route)
    }
    fun navigateToWebServices() {
        navController.navigate(WebScreen.WebServices.route)
    }

    fun navigateToProductDetail(productId: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            val productId_ = Uri.encode(productId)
            navController.navigate(WebScreen.ProductDetail.createRoute(productId_))
        }
    }

    fun navigateToArticleDetail(articleId: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            val articleId_ = Uri.encode(articleId)
            navController.navigate(WebScreen.ArticleDetail.createRoute(articleId_))
        }
    }

    fun navigateToAlbumDetail(albumId: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            val albumId_ = Uri.encode(albumId)
            navController.navigate(WebScreen.AlbumDetail.createRoute(albumId_))
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    @Suppress("DEPRECATION")
    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            cm?.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
