package com.kamford.app.data.local.albums

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class AlbumsStore(
    private val albumsDao: AlbumsDao
) {

    fun albumListAll(
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Album>> {
        return albumsDao.albumListAll(limit)
    }

    fun albumListByAuthor(
        author: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Album>> {
        return albumsDao.albumListByAuthor(author,limit)
    }

    fun albumListByTypeId(
        author: String,
        typeId: Int,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Album>> {
        return albumsDao.albumListByAuthorAndTypeId(author,typeId,limit)
    }

    fun albumListByCourseId(
        author: String,
        courseId: String,
        limit: Int = Integer.MAX_VALUE
    ): Flow<List<Album>> {
        return albumsDao.albumListByAuthorAndCourseId(author,courseId,limit)
    }


    suspend fun addAlbum(album: Album?): String {
        if (album == null){
            return "null"
        }else{
            return when (val local = albumsDao.getAlbumById(album.id)) {
                null -> albumsDao.insert(album).toString()
                else -> {
                    albumsDao.update(album)
                    local.id
                }
            }
        }
    }

    fun findAlbumById(id: String): Flow<Album> {
        return albumsDao.findAlbumById(id)
    }

    suspend fun addAlbumList(albums: Collection<Album>?) {
        if (!albums.isNullOrEmpty()){
            albumsDao.insertAll(albums)
        }
    }

    suspend fun isEmpty(): Boolean = albumsDao.count() == 0
}