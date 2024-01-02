package com.kamford.app.compose

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object KamfordDestinations {
    const val BOOKMARK_ROUTE = "bookmark"
    const val QUESTION_ROUTE = "Question"
    const val USER_ROUTE = "user"


    const val WEB_HOME_ROUTE = "web-home"
    const val ABOUT_US_ROUTE = "web-about-us"
    const val NEWS_ROUTE = "web-news"
    const val PRODUCTS_ROUTE = "web-products"
    const val HEALTHS_ROUTE = "web-healths"
    const val SERVICES_ROUTE = "web-services"
}

class KamfordNavigationActions(
    navController: NavHostController
) {
    val navigateToWebHome: () -> Unit = {
        navController.navigate(KamfordDestinations.WEB_HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToAboutUs: () -> Unit = {
        navController.navigate(KamfordDestinations.ABOUT_US_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToNews: () -> Unit = {
        navController.navigate(KamfordDestinations.NEWS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToProducts: () -> Unit = {
        navController.navigate(KamfordDestinations.PRODUCTS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToHealths: () -> Unit = {
        navController.navigate(KamfordDestinations.HEALTHS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToServices: () -> Unit = {
        navController.navigate(KamfordDestinations.SERVICES_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToUser: () -> Unit = {
        navController.navigate(KamfordDestinations.USER_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
//    val navigateToBookmark: () -> Unit = {
//        navController.navigate(KamfordDestinations.BOOKMARK_ROUTE) {
//            popUpTo(navController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
//    }
//    val navigateToQuestion: () -> Unit = {
//        navController.navigate(KamfordDestinations.QUESTION_ROUTE) {
//            popUpTo(navController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
//    }



}