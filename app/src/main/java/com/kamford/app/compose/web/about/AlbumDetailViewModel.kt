package com.kamford.app.compose.web.about

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.albums.Album
import com.kamford.app.data.local.attachments.Attachment
import com.kamford.app.data.local.albums.AlbumsStore
import com.kamford.app.data.local.albums.asStoreAlbum
import com.kamford.app.data.local.attachments.AttachmentsStore
import com.kamford.app.data.local.attachments.asStoreAttachmentList
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val albumId: String?,
    private val albumsStore: AlbumsStore = RoomModule.albumsStore,
    private val attachmentStore: AttachmentsStore = RoomModule.attachmentStore
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(AlbumDetailViewState())
    val state: StateFlow<AlbumDetailViewState>
        get() = _state

    init {
        viewModelScope.launch {
            if (albumId != null){
                combine(
                    attachmentStore.attachmentListByAlbumId(albumId).onEach {
                        if (it.isEmpty()) {
                            refreshing.value = true
                        }
                    },
                    albumsStore.findAlbumById(albumId),
                    refreshing
                ) {attachmentList, albumFlow, refreshing ->
                    AlbumDetailViewState(
                        attachmentList = attachmentList,
                        selectedAlbum = albumFlow,
                        refreshing = refreshing
                    )
                }.collect {
                    _state.value = it
                    refresh(_state.value.refreshing)
                }
            }
        }
    }

    private fun refresh(force: Boolean) {
        if (force && !albumId.isNullOrEmpty()){
            viewModelScope.launch {
                runCatching {
                    try {
                        val responseAlbumItem = repository.getWebAlbumDetail(albumId).data.albumItem
                        attachmentStore.addAttachmentList(responseAlbumItem?.asStoreAttachmentList())
                        albumsStore.addAlbum(responseAlbumItem?.asStoreAlbum())
                        AlbumDetailViewState(
                            attachmentList = responseAlbumItem?.asStoreAttachmentList()?.toList(),
                            selectedAlbum = responseAlbumItem?.asStoreAlbum()
                        )
                    } catch (e: Exception) {
                        Log.d("TAG", "getArticleFeed: ${e}")
                    }
                }
                refreshing.value = false
            }
        }
    }

}

data class AlbumDetailViewState(
    val attachmentList: List<Attachment>? = null,
    val selectedAlbum: Album? = null,
    val refreshing: Boolean = false,
)