package com.kamford.app.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kamford.app.R
import com.kamford.app.ui.theme.KamfordAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KamfordApp(
    widthSizeClass: WindowWidthSizeClass,
) {
    KamfordAppTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            KamfordNavigationActions(navController)
        }

        val coroutineScope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: KamfordDestinations.WEB_HOME_ROUTE

        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

        ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    navigateToWebHome = navigationActions.navigateToWebHome,
                    navigateToAboutUs = navigationActions.navigateToAboutUs,
                    navigateToNews = navigationActions.navigateToNews,
                    navigateToProducts = navigationActions.navigateToProducts,
                    navigateToHealths = navigationActions.navigateToHealths,
                    navigateToServices = navigationActions.navigateToServices,
                    navigateToUser = navigationActions.navigateToUser,
//                    navigateToQuestion = navigationActions.navigateToQuestion,
//                    navigateToBookmark = navigationActions.navigateToBookmark,
                    closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
                )
            },
            drawerState = sizeAwareDrawerState,
            gesturesEnabled = !isExpandedScreen
        ) {
            Row {
                if (isExpandedScreen) {
                    AppNavRail(
                        currentRoute = currentRoute,
                        navigateToWebHome = navigationActions.navigateToWebHome,
                        navigateToAboutUs = navigationActions.navigateToAboutUs,
                        navigateToNews = navigationActions.navigateToNews,
                        navigateToProducts = navigationActions.navigateToProducts,
                        navigateToHealths = navigationActions.navigateToHealths,
                        navigateToServices = navigationActions.navigateToServices,
                        navigateToUser = navigationActions.navigateToUser,
//                        navigateToQuestion = navigationActions.navigateToQuestion,
//                        navigateToBookmark = navigationActions.navigateToBookmark,
                    )
                }
                KamfordNavGraph(
                    navController = navController,
                    openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    return if (!isExpandedScreen) {
        drawerState
    } else {
        DrawerState(DrawerValue.Closed)
    }
}


@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        icon= {
            Icon(
                painterResource(R.drawable.ic_kamford_logo),
                contentDescription = stringResource(id = R.string.app_name),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
            )
        },
        onDismissRequest = {  },
        title = { Text(text = "Connection error!!") },
        text = { Text(text = "Unable to fetch podcasts feeds.Check your internet connection and try again.") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        }
    )
}
