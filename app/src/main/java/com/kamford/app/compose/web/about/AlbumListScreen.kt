package com.kamford.app.compose.web.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.kamford.app.data.local.albums.Album

@Composable
fun AlbumListScreen(
    courseId: String,
    modifier: Modifier = Modifier,
    onAlbumClick: (Album) -> Unit,
) {
    val viewModel: AlbumListViewModel = viewModel(
        key = "web_album_${courseId}",
        factory = viewModelProviderFactoryOf { AlbumListViewModel(courseId) }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val albumList = viewState.albumList

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.testTag("album_list")
            .imePadding(),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 12.dp
        )
    ) {
        if (albumList != null){
            items(
                items = albumList,
                key = { it.id }
            ) { album ->
                AlbumItem(album = album) {
                    onAlbumClick(album)
                }
            }
        }

    }
}

@Composable
fun AlbumItem(album: Album, onAlbumClick: (Album) -> Unit) {
    ImageListItem(album, onAlbumClick = onAlbumClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListItem(album: Album, onAlbumClick: (Album) -> Unit) {
    Card(
        onClick = { onAlbumClick(album) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            ItemGlideImage(
                model = album.image_s_url,
                contentDescription = album.name ?: "image description",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = album.name ?: "name",
                textAlign = TextAlign.Center,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

