package com.kamford.app.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kamford.app.compose.question.KamfordQuestionApp
import com.kamford.app.compose.user.KamfordUserApp
import com.kamford.app.compose.web.KamfordWebApp
import com.kamford.app.compose.web.WebScreen

@Composable
fun KamfordNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = KamfordDestinations.WEB_HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(KamfordDestinations.WEB_HOME_ROUTE) {
            KamfordWebApp(
                openDrawer = openDrawer,
                startRoute = WebScreen.WebHome.route
            )
        }

        composable(KamfordDestinations.ABOUT_US_ROUTE) {
            KamfordWebApp(
                openDrawer = openDrawer,
                startRoute = WebScreen.WebAboutUs.route
            )
        }

        composable(KamfordDestinations.NEWS_ROUTE) {
            KamfordWebApp(
                openDrawer = openDrawer,
                startRoute = WebScreen.WebNews.route
            )
        }

        composable(KamfordDestinations.PRODUCTS_ROUTE) {
            KamfordWebApp(
                openDrawer = openDrawer,
                startRoute = WebScreen.WebProducts.route
            )
        }

        composable(KamfordDestinations.HEALTHS_ROUTE) {
            KamfordWebApp(
                openDrawer = openDrawer,
                startRoute = WebScreen.WebHealths.route
            )
        }

        composable(KamfordDestinations.SERVICES_ROUTE) {
            KamfordWebApp(
                openDrawer = openDrawer,
                startRoute = WebScreen.WebServices.route
            )
        }

        composable(KamfordDestinations.USER_ROUTE) {
            KamfordUserApp(
                openDrawer = openDrawer
            )
        }

//        composable(KamfordDestinations.QUESTION_ROUTE) {
//            KamfordQuestionApp(
//                openDrawer = openDrawer
//            )
//        }
//
//        composable(KamfordDestinations.BOOKMARK_ROUTE) {
//            KamfordUserApp(
//                openDrawer = openDrawer
//            )
//        }

    }
}
