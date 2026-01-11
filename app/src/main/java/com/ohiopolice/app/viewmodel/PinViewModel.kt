package com.ohiopolice.app.viewmodel

import androidx.lifecycle.ViewModel
import com.ohiopolice.app.data.PinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PinViewModel(private val pinRepository: PinRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PinUiState())
    val uiState: StateFlow<PinUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = _uiState.value.copy(isPinSet = pinRepository.isPinSet())
    }

    fun onPinChanged(value: String) {
        _uiState.value = _uiState.value.copy(pinInput = value, error = null)
    }

    fun submitPin() {
        val pin = _uiState.value.pinInput
        if (pin.length < 4) {
            _uiState.value = _uiState.value.copy(error = "PIN must be at least 4 digits")
            return
        }
        if (pinRepository.isPinSet()) {
            if (pinRepository.validatePin(pin)) {
                _uiState.value = _uiState.value.copy(isAuthenticated = true)
            } else {
                _uiState.value = _uiState.value.copy(error = "Invalid PIN")
            }
        } else {
            pinRepository.setPin(pin)
            _uiState.value = _uiState.value.copy(isAuthenticated = true, isPinSet = true)
        }
    }
}

 data class PinUiState(
    val pinInput: String = "",
    val isPinSet: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null
)
