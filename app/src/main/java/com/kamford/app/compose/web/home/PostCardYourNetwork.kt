package com.kamford.app.compose.web.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.data.local.products.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCardPopular(
    post: Product,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onProductClick(post) },
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .width(280.dp)
    ) {
        Column {
            ItemGlideImage(
                model = post.image_m_url,
                contentDescription = post.name ?: "image description",
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.name ?: "name",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = post.author ?: "author",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "abc",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
