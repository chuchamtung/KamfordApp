package com.kamford.app.compose.web.about

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamford.app.data.local.albums.Album
import com.kamford.app.data.local.albums.AlbumsStore
import com.kamford.app.data.local.albums.asStoreAlbumList
import com.kamford.app.di.RoomModule
import com.kamford.app.data.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val courseId: String,
    private val albumsStore: AlbumsStore = RoomModule.albumsStore,
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)
    private val repository = MainRepository()

    private val _state = MutableStateFlow(AlbumViewState())
    val state: StateFlow<AlbumViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                albumsStore.albumListByCourseId(
                    author = "kamford_web",
                    courseId = courseId
                ).onEach {
                    if (it.isEmpty()) {
                        refreshing.value = true
                    }
                },
                refreshing
            ) {albumList, refreshing ->
                AlbumViewState(
                    albumList = albumList,
                    refreshing = refreshing
                )
            }.collect {
                _state.value = it
                refresh(_state.value.refreshing)
            }

        }
    }

    private fun refresh(force: Boolean) {
        if (force){
            viewModelScope.launch {
                runCatching {
                    try {
                        val storeAlbumList = repository.getWebCourseItem(courseId,-1,0,0,0).data.asStoreAlbumList()
                        albumsStore.addAlbumList(storeAlbumList)
                        AlbumViewState(
                            albumList = storeAlbumList?.toList()
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

data class AlbumViewState(
    val albumList: List<Album>? = emptyList(),
    val refreshing: Boolean = false,
)