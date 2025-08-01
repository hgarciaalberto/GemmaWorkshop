package com.gdgedinburgh.gemmaworkshop.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperator
import com.gdgedinburgh.gemmaworkshop.model.GemmaUiState
import com.gdgedinburgh.gemmaworkshop.model.MODEL_PREFIX
import com.gdgedinburgh.gemmaworkshop.model.USER_PREFIX
import com.gdgedinburgh.gemmaworkshop.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

// 9.
@KoinViewModel
class ChatViewModel(
    val llmOperator: LLMOperator,
) : ViewModel() {

    // `GemmaUiState()` is optimized for the Gemma model.
    // Replace `GemmaUiState` with `ChatUiState()` if you're using a different model
    private val _uiState: MutableStateFlow<GemmaUiState> = MutableStateFlow(GemmaUiState())
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val _textInputEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val isTextInputEnabled: StateFlow<Boolean> =
        _textInputEnabled.asStateFlow()

    fun sendMessage(userMessage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.addMessage(userMessage, USER_PREFIX)
            _uiState.value.createLoadingMessage()
            setInputEnabled(false)
            try {
                val fullPrompt = _uiState.value.fullPrompt
                llmOperator.generateResponse(fullPrompt).let { message ->
                    setInputEnabled(true)
                    _uiState.value.deleteLastMessage()
                    _uiState.value.addMessage(message, "test")
                }

//                llmOperator.generateResponseAsync(fullPrompt)
//                    .collectIndexed { index, (partialResult, done) ->
//                        currentMessageId?.let {
//                            if (index == 0) {
//                                _uiState.value.appendFirstMessage(it, partialResult)
//                            } else {
//                                _uiState.value.appendMessage(it, partialResult, done)
//                            }
//                            if (done) {
//                                currentMessageId = null
//                                // Re-enable text input
//                                setInputEnabled(true)
//                            }
//                        }
//                    }
            } catch (e: Exception) {
                _uiState.value.addMessage(e.message ?: "Unknown Error", MODEL_PREFIX)
                setInputEnabled(true)
            }
        }
    }

    private fun setInputEnabled(isEnabled: Boolean) {
        _textInputEnabled.value = isEnabled
    }
}
