package com.ohiopolice.app.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class OpenAiClient(
    private val apiKey: String,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    private val client = OkHttpClient()

    suspend fun chat(messages: List<ChatMessage>): String {
        val requestBody = ChatRequest(
            model = "gpt-4o-mini",
            messages = messages
        )
        val body = json.encodeToString(requestBody).toRequestBody(JSON)
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("OpenAI request failed: ${response.code}")
            }
            val payload = response.body?.string().orEmpty()
            val parsed = json.decodeFromString<ChatResponse>(payload)
            return parsed.choices.firstOrNull()?.message?.content.orEmpty()
        }
    }

    @Serializable
    data class ChatRequest(
        val model: String,
        val messages: List<ChatMessage>
    )

    @Serializable
    data class ChatMessage(
        val role: String,
        val content: String
    )

    @Serializable
    data class ChatResponse(
        val choices: List<Choice> = emptyList()
    )

    @Serializable
    data class Choice(
        val message: ChatMessage
    )

    companion object {
        private val JSON = "application/json".toMediaType()
    }
}
