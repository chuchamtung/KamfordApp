package com.kamford.app.data.local.albums

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
@Entity(
    tableName = "albums",
    indices = [
        Index("course_id"),
        Index("author")
    ]
)
@Immutable
data class Album(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "created_at") var createdAt: Long?,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "is_type") var isType: Int = 0,
    @ColumnInfo(name = "tags") var tags: String? = null,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "course_id") var courseId: String? = null,
    @ColumnInfo(name = "image_id") var imageId: String? = null,

    @ColumnInfo(name = "view_count") var viewCount: Int = 0,
    @ColumnInfo(name = "replies_count") var repliesCount: Int = 0,
    @ColumnInfo(name = "favorite_count") var favoriteCount: Int = 0,
    @ColumnInfo(name = "bookmark_count") var bookmarkCount: Int = 0,

    @ColumnInfo(name = "image_l_url") val image_l_url: String? = null,
    @ColumnInfo(name = "image_m_url") val image_m_url: String? = null,
    @ColumnInfo(name = "image_s_url") val image_s_url: String? = null,
    @ColumnInfo(name = "web_album_detail_url") val web_album_detail_url: String? = null,
    @ColumnInfo(name = "blog_album_detail_url") val blog_album_detail_url: String? = null
)