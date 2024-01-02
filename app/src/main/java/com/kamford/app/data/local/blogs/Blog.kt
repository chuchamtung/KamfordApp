package com.kamford.app.data.local.blogs

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
    tableName = "blogs",
    indices = [
        Index("id", unique = true),
        Index("name", unique = true),
        Index("author", unique = true)
    ]
)
@Immutable
data class Blog(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "created_at") var createdAt: Long = 0L,
    @ColumnInfo(name = "updated_at") var updatedAt: Long = 0L,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "alias") var alias: String? = null,
    @ColumnInfo(name = "hosts") var hosts: String? = null,
    @ColumnInfo(name = "logo") var logo: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "is_type") var isType: Int = 0,
    @ColumnInfo(name = "is_value") var isValue: Int = 0,
    @ColumnInfo(name = "tags") var tags: String? = null,
    @ColumnInfo(name = "author") var author: String? = null
)