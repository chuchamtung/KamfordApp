package com.kamford.app.compose.web.product

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.R
import com.kamford.app.compose.utils.BookmarkButton
import com.kamford.app.compose.utils.DetailTopAppBar
import com.kamford.app.compose.utils.FavoriteButton
import com.kamford.app.compose.utils.HtmlImageToText
import com.kamford.app.compose.utils.HtmlToParagraphs
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.ParagraphToScreen
import com.kamford.app.compose.utils.ShareButton
import com.kamford.app.compose.utils.TextSettingsButton
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.paragraphs.Paragraphs
import com.kamford.app.data.local.paragraphs.ParagraphsType
import com.kamford.app.data.local.products.Product
import com.kamford.app.di.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String?,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    state: LazyListState = rememberLazyListState(),
) {
    val viewModel: ProductDetailViewModel = viewModel(
        key = "web_product_detail_${productId}",
        factory = viewModelProviderFactoryOf { ProductDetailViewModel(productId) }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val selectedProduct_ = viewState.selectedProduct
    val productCourse_ = viewState.productCourse

    var showUnimplementedActionDialog by rememberSaveable { mutableStateOf(false) }
    if (showUnimplementedActionDialog) {
        FunctionalityNotAvailablePopup {
            showUnimplementedActionDialog = false
        }
    }

    Row(modifier.fillMaxSize()) {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                DetailTopAppBar(
                    title = productCourse_?.name?:"Course name",
                    isFullScreen = true,
                    onBackPressed = navigateBack
                )
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        FavoriteButton(onClick = { showUnimplementedActionDialog = true })
                        BookmarkButton(isBookmarked = false, onClick = { showUnimplementedActionDialog = true })
                        if (selectedProduct_!= null){
                            ShareButton(onClick = { sharePost(selectedProduct_, context) })
                        }
                        TextSettingsButton(onClick = { showUnimplementedActionDialog = true })
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding),
                state = state
            ) {
                val paragraphList = HtmlImageToText(selectedProduct_?.description ?: "<p>Product Description - ${productId} - </p>", selectedProduct_?.id ?: "product-id")
                items(paragraphList) {
                    ParagraphToProduct(paragraph = it)
                    ///ParagraphToScreen(paragraphs = it)
                }

            }
        }
    }

}

@Composable
private fun ParagraphToProduct(
    paragraph: Paragraphs,
    modifier: Modifier = Modifier
) {
    Box {
        when (paragraph.paragraphType) {
            ParagraphsType.Text -> Text(
                text = paragraph.paragraphText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.dp)
            )
            ParagraphsType.Image -> ItemGlideImage(
                model = Constants.KAMFORD_APP_WEB + paragraph.paragraphText,
                contentDescription = null,
                modifier = modifier.fillMaxWidth()
                    .heightIn(min = 380.dp),
                contentScale = ContentScale.FillWidth,
            )
            else -> {}
        }
    }
}


@Composable
private fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = stringResource(id = R.string.article_functionality_not_available),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    )
}

private fun sharePost(product: Product, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, product.name ?: "product title")
        putExtra(Intent.EXTRA_TEXT, product.web_product_detail_url)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.article_share_post)
        )
    )
}