package com.ohiopolice.app.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohiopolice.app.data.AiChatDao
import com.ohiopolice.app.data.AiChatMessage
import com.ohiopolice.app.network.OpenAiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AiChatViewModel(
    private val context: Context,
    private val aiChatDao: AiChatDao,
    private val openAiClient: OpenAiClient
) : ViewModel() {
    val chatHistory: Flow<List<AiChatMessage>> = aiChatDao.observeChat()

    private val _uiState = MutableStateFlow(AiChatUiState(isOffline = !isOnline()))
    val uiState: StateFlow<AiChatUiState> = _uiState.asStateFlow()

    fun updateInput(value: String) {
        _uiState.value = _uiState.value.copy(input = value)
    }

    fun sendMessage(orcContext: String) {
        val prompt = _uiState.value.input.trim()
        if (prompt.isBlank()) return

        val online = isOnline()
        _uiState.value = _uiState.value.copy(isOffline = !online)
        if (!online) {
            _uiState.value = _uiState.value.copy(error = "Offline: AI guidance unavailable.")
            return
        }

        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()
            aiChatDao.insertMessage(
                AiChatMessage(timestamp = timestamp, role = "user", content = prompt)
            )
            _uiState.value = _uiState.value.copy(input = "", error = null, isLoading = true)

            val systemContext = "You are an assistant for Ohio peace officers. Provide guidance based on Ohio Revised Code context only."
            val response = openAiClient.chat(
                listOf(
                    OpenAiClient.ChatMessage("system", systemContext),
                    OpenAiClient.ChatMessage("system", "Ohio Revised Code context: $orcContext"),
                    OpenAiClient.ChatMessage("user", prompt)
                )
            )
            aiChatDao.insertMessage(
                AiChatMessage(timestamp = System.currentTimeMillis(), role = "assistant", content = response)
            )
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            aiChatDao.clearChat()
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

data class AiChatUiState(
    val input: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false
)
