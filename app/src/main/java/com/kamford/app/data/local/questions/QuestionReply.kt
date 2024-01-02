package com.kamford.app.data.local.questions

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
    tableName = "question_replies",
    indices = [
        Index("author"),
        Index("quote_id")
    ]
)
@Immutable
data class QuestionReply(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "created_at") var createdAt: Long?,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "quote_id") var quoteId: String? = null,
    @ColumnInfo(name = "Is_type") var isType: Int = 0,
    @ColumnInfo(name = "attachment_id") var attachmentId: String? = null,
    @ColumnInfo(name = "content") var content: String? = null,
    @ColumnInfo(name = "ip_address") var ipAddress: String? = null,
    @ColumnInfo(name = "ip_resource") var ipResource: String? = null,
    @ColumnInfo(name = "client") var client: String? = null
)