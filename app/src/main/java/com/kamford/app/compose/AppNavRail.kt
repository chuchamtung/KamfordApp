package com.kamford.app.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamford.app.R
import com.kamford.app.ui.theme.KamfordAppTheme

@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToWebHome: () -> Unit,
    navigateToAboutUs: () -> Unit,
    navigateToNews: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToHealths: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToUser: () -> Unit,
//    navigateToQuestion: () -> Unit,
//    navigateToBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        header = {
            Icon(
                painterResource(R.drawable.ic_kamford_logo),
                null,
                Modifier.padding(vertical = 12.dp).size(width = 36.dp, height = 36.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.WEB_HOME_ROUTE,
            onClick = navigateToWebHome,
            icon = { Icon(Icons.Filled.Web, "Web Home") },
            label = { Text("Web") },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.ABOUT_US_ROUTE,
            onClick = navigateToAboutUs,
            icon = { Icon(painterResource(R.drawable.ic_kamford_logo), "About US") },
            label = { Text("About") },
            alwaysShowLabel = false

        )
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.NEWS_ROUTE,
            onClick = navigateToNews,
            icon = { Icon(Icons.Filled.Newspaper, "News") },
            label = { Text("News") },
            alwaysShowLabel = false

        )
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.PRODUCTS_ROUTE,
            onClick = navigateToProducts,
            icon = { Icon(Icons.Filled.ShoppingCart, "Products") },
            label = { Text("Products") },
            alwaysShowLabel = false

        )
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.HEALTHS_ROUTE,
            onClick = navigateToHealths,
            icon = { Icon(Icons.Filled.HealthAndSafety, "Healths") },
            label = { Text("Healths") },
            alwaysShowLabel = false

        )
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.SERVICES_ROUTE,
            onClick = navigateToServices,
            icon = { Icon(Icons.Filled.MiscellaneousServices, "Services") },
            label = { Text("Services") },
            alwaysShowLabel = false

        )
        NavigationRailItem(
            selected = currentRoute == KamfordDestinations.USER_ROUTE,
            onClick = navigateToUser,
            icon = { Icon(Icons.Outlined.AccountBox, "User") },
            label = { Text("User") },
            alwaysShowLabel = false
        )
//        NavigationRailItem(
//            selected = currentRoute == KamfordDestinations.QUESTION_ROUTE,
//            onClick = navigateToQuestion,
//            icon = { Icon(Icons.Outlined.Chat, "Question") },
//            label = { Text("Question") },
//            alwaysShowLabel = false
//        )
//        NavigationRailItem(
//            selected = currentRoute == KamfordDestinations.BOOKMARK_ROUTE,
//            onClick = navigateToBookmark,
//            icon = { Icon(Icons.Outlined.Bookmarks, "Bookmark") },
//            label = { Text("Bookmark") },
//            alwaysShowLabel = false
//        )
        Spacer(Modifier.weight(1f))
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    KamfordAppTheme {
        AppNavRail(
            currentRoute = KamfordDestinations.WEB_HOME_ROUTE,
            navigateToWebHome = {},
            navigateToAboutUs = {},
            navigateToNews = {},
            navigateToProducts = {},
            navigateToHealths = {},
            navigateToServices = {},
            navigateToUser = {},
//            navigateToQuestion = {},
//            navigateToBookmark = {},
        )
    }
}
