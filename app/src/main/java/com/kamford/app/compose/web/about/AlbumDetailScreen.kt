package com.kamford.app.compose.web.about

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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kamford.app.R
import com.kamford.app.compose.utils.BookmarkButton
import com.kamford.app.compose.utils.DetailTopAppBar
import com.kamford.app.compose.utils.FavoriteButton
import com.kamford.app.compose.utils.ItemGlideImage
import com.kamford.app.compose.utils.ShareButton
import com.kamford.app.compose.utils.TextSettingsButton
import com.kamford.app.compose.utils.viewModelProviderFactoryOf
import com.kamford.app.data.local.albums.Album
import com.kamford.app.data.local.attachments.Attachment
import com.kamford.app.di.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumId: String?,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val viewModel: AlbumDetailViewModel = viewModel(
        key = "web_album_detail_${albumId}",
        factory = viewModelProviderFactoryOf { AlbumDetailViewModel(albumId) }
    )
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val selectedAlbum_ = viewState.selectedAlbum
    val attachmentList_ = viewState.attachmentList

    var showUnimplementedActionDialog by rememberSaveable { mutableStateOf(false) }
    if (showUnimplementedActionDialog) {
        FunctionalityNotAvailablePopup { showUnimplementedActionDialog = false }
    }


    Row(modifier.fillMaxSize()) {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                DetailTopAppBar(
                    title = selectedAlbum_?.name?:"Album name",
                    isFullScreen = true,
                    onBackPressed = navigateBack
                )
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        FavoriteButton(onClick = { showUnimplementedActionDialog = true })
                        BookmarkButton(isBookmarked = true, onClick = { showUnimplementedActionDialog = true })
                        if (selectedAlbum_!= null){
                            ShareButton(onClick = { sharePost(selectedAlbum_, context) })
                        }
                        TextSettingsButton(onClick = { showUnimplementedActionDialog = true })
                    }
                )
            },
            modifier = modifier
        ) { innerPadding ->

            LazyColumn(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(innerPadding)
            ) {
                if (attachmentList_ != null){
                    items(
                        items = attachmentList_,
                        key = { it.id }
                    ) { attachment ->
                        AttachmentBox(attachment = attachment)
                    }
                }
            }
        }

    }


}

@Composable
private fun AttachmentBox(attachment: Attachment) {
    Box {
        val imageModifier = Modifier
            .heightIn(min = 180.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)

        ItemGlideImage(
            model = attachment.image_l_url,
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
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



private fun sharePost(album: Album?, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, album?.name ?: "album title")
        putExtra(Intent.EXTRA_TEXT, album?.blog_album_detail_url)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.article_share_post)
        )
    )
}
