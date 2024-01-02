package com.kamford.app.data.local.users

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
    tableName = "users",
    indices = [
        Index("name"),
        Index("android_id"),
        Index("android_finger")
    ]
)
@Immutable
data class User(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "created_at") var createdAt: Long?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "nick_name") var nickName: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "avatar") var avatar: String? = null,
    @ColumnInfo(name = "blog_name") var blogName: String? = null,
    @ColumnInfo(name = "blog_alias") var blogAlias: String? = null,

    @ColumnInfo(name = "android_id") var androidId: String? = null,
    @ColumnInfo(name = "android_finger") var androidFinger: String? = null,
    @ColumnInfo(name = "android_agent") var androidAgent: String? = null,
    @ColumnInfo(name = "user_key") var userKey: String? = null,
    @ColumnInfo(name = "login_state") var loginState: Int = 0,
    @ColumnInfo(name = "is_blog") var isBlog: Int = 0,
)