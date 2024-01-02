package com.kamford.app.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamford.app.R
import com.kamford.app.ui.theme.KamfordAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
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
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier) {
        KamfordLogo(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 24.dp),
            closeDrawer = closeDrawer
        )
        NavigationDrawerItem(
            label = { Text("Home") },
            icon = { Icon(Icons.Filled.Web, "Home") },
            selected = currentRoute == KamfordDestinations.WEB_HOME_ROUTE,
            onClick = { navigateToWebHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("About Us / 關於金科") },
            icon = { Icon(painterResource(R.drawable.ic_kamford_logo), "About Us") },
            selected = currentRoute == KamfordDestinations.ABOUT_US_ROUTE,
            onClick = { navigateToAboutUs(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("News / 企業動態") },
            icon = { Icon(Icons.Filled.Newspaper, "News") },
            selected = currentRoute == KamfordDestinations.NEWS_ROUTE,
            onClick = { navigateToNews(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Products / 產品") },
            icon = { Icon(Icons.Filled.ShoppingCart, "Products") },
            selected = currentRoute == KamfordDestinations.PRODUCTS_ROUTE,
            onClick = { navigateToProducts(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Healths / 養生課堂") },
            icon = { Icon(Icons.Filled.HealthAndSafety, "Healths") },
            selected = currentRoute == KamfordDestinations.HEALTHS_ROUTE,
            onClick = { navigateToHealths(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Service / 售後服務") },
            icon = { Icon(Icons.Filled.MiscellaneousServices, "Service") },
            selected = currentRoute == KamfordDestinations.SERVICES_ROUTE,
            onClick = { navigateToServices(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("User") },
            icon = { Icon(Icons.Outlined.AccountBox, "User") },
            selected = currentRoute == KamfordDestinations.USER_ROUTE,
            onClick = { navigateToUser(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
//        NavigationDrawerItem(
//            label = { Text("Question / 在線咨詢") },
//            icon = { Icon(Icons.Outlined.Chat, "Question") },
//            selected = currentRoute == KamfordDestinations.QUESTION_ROUTE,
//            onClick = { navigateToQuestion(); closeDrawer() },
//            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//        )
//        NavigationDrawerItem(
//            label = { Text("Bookmark / 書籤") },
//            icon = { Icon(Icons.Outlined.Bookmarks, "Bookmark") },
//            selected = currentRoute == KamfordDestinations.BOOKMARK_ROUTE,
//            onClick = { navigateToBookmark(); closeDrawer() },
//            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//        )
    }
}

@Composable
private fun KamfordLogo(
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(R.drawable.ic_kamford_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .width(36.dp)
                .height(36.dp)
        )
        Text(
            text = "Kamford 金科偉業",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = closeDrawer ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close)
            )
        }
    }
}


@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    KamfordAppTheme {
        AppDrawer(
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
            closeDrawer = { }
        )
    }
}