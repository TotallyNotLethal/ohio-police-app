package com.ohiopolice.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AiChatDao {
    @Query("SELECT * FROM ai_chat_messages ORDER BY timestamp")
    fun observeChat(): Flow<List<AiChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: AiChatMessage)

    @Query("DELETE FROM ai_chat_messages")
    suspend fun clearChat()
}
