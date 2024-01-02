package com.kamford.app.data.local.attachments

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
    tableName = "attachments",
    indices = [
        Index("author"),
        Index("quote_id")
    ]
)
@Immutable
data class Attachment(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "created_at") var createdAt: Long?,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "quote_id") var quoteId: String? = null,
    @ColumnInfo(name = "album_id") var albumId: String? = null,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "is_type") var isType: Int = 0,
    @ColumnInfo(name = "view_count") var viewCount: Int = 0,

    @ColumnInfo(name = "image_l_url") val image_l_url: String? = null,
    @ColumnInfo(name = "image_m_url") val image_m_url: String? = null,
    @ColumnInfo(name = "image_s_url") val image_s_url: String? = null
)