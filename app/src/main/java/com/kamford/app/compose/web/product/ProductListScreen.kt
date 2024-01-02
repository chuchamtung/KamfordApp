package com.kamford.app.compose.web.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.products.Product


@Composable
fun ProductListScreen(
    courseId: String,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit,
    state: LazyGridState = rememberLazyGridState()
) {
    val viewModel: ProductListViewMode = viewModel(
        key = "web_product_${courseId}",
        factory = viewModelProviderFactoryOf { ProductListViewMode(courseId) }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val productList = viewState.productList

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .testTag("product_list")
            .imePadding(),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 12.dp
        ),
        state = state
    ) {
        if (productList != null){
            items(
                items = productList,
                key = { it.id }
            ) { product ->
                ProductItem(product = product) {
                    onProductClick(product)
                }
            }
        }

    }
}

@Composable
fun ProductItem(product: Product, onProductClick: (Product) -> Unit) {
    ProductListItem(product, onProductClick = onProductClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(product: Product, onProductClick: (Product) -> Unit) {
    Card(
        onClick = { onProductClick(product) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            ItemGlideImage(
                model = product.image_m_url,
                contentDescription = product.name ?: "image description",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = product.name ?: "name",
                textAlign = TextAlign.Center,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}