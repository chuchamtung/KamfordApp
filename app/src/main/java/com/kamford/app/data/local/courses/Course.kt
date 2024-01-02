package com.kamford.app.data.local.courses

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
    tableName = "courses",
    indices = [
        Index("parent_id"),
        Index("author")
    ]
)
@Immutable
data class Course(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "path") var path: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "is_type") var isType: Int = 0,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "model_id") var modelId: String? = null,
    @ColumnInfo(name = "parent_id") var parentId: String? = null,

    @ColumnInfo(name = "web_course_url") val webCourseUrl: String,
    @ColumnInfo(name = "web_course_page_url") val webCoursePageUrl: String? = null,
    @ColumnInfo(name = "blog_course_url") val blogCourseUrl: String? = null,

    @ColumnInfo(name = "text_content") var textContent: String? = null,
)