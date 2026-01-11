package com.ohiopolice.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class OrcStatute(
    @SerialName("title") val title: String,
    @SerialName("chapter") val chapter: String,
    @SerialName("section") val section: String,
    @SerialName("heading") val heading: String,
    @SerialName("body") val body: String
)

@Entity(tableName = "favorite_statutes")
 data class FavoriteStatute(
    @PrimaryKey val section: String,
    val title: String,
    val chapter: String,
    val heading: String,
    val body: String
)

@Entity(tableName = "ai_chat_messages")
 data class AiChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val timestamp: Long,
    val role: String,
    val content: String
)
