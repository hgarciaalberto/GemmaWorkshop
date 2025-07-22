package com.gdgedinburgh.gemmaworkshop.llm

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class LLMOperatorFactory  {

    private lateinit var llmInferenceDelegate: LLMOperatorSwift

    fun initialize(llmInferenceDelegate: LLMOperatorSwift) {
        this.llmInferenceDelegate = llmInferenceDelegate
    }

    actual fun create(): LLMOperator {
        if (!::llmInferenceDelegate.isInitialized) {
            throw IllegalStateException("LLMOperatorFactory not initialized")
        }
        return LLMOperatorIOSImpl(llmInferenceDelegate)
    }
}

class LLMOperatorIOSImpl(private val delegate: LLMOperatorSwift) : LLMOperator {

    private val initialized = atomic<Boolean>(false)

    override suspend fun initModel(): String? {
        if (initialized.value) {
            return null
        }
        return try {
            delegate.loadModel(MODEL_NAME)
            initialized.value = true
            null
        } catch (e: Exception) {
            e.message
        }
    }

    override fun sizeInTokens(text: String): Int = -1 // TODO

    override suspend fun generateResponse(inputText: String): String {
        if (initialized.value.not()) {
            throw IllegalStateException("LLMInference is not initialized properly")
        }
        return delegate.generateResponse(inputText)
    }

    override suspend fun generateResponseAsync(inputText: String): Flow<Pair<String, Boolean>> {
        if (initialized.value.not()) {
            throw IllegalStateException("LLMInference is not initialized properly")
        }
        val partialResultsFlow = MutableSharedFlow<Pair<String, Boolean>>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        delegate.generateResponseAsync(inputText, { partialResponse ->
            partialResultsFlow.tryEmit(partialResponse to false)
        }, { completion ->
            partialResultsFlow.tryEmit(completion to true)
        })
        return partialResultsFlow.asSharedFlow()
    }

}

interface LLMOperatorSwift {
    suspend fun loadModel(modelName: String)
    fun sizeInTokens(text: String): Int
    suspend fun generateResponse(inputText: String): String
    suspend fun generateResponseAsync(
        inputText: String,
        progress: (partialResponse: String) -> Unit,
        completion: (completeResponse: String) -> Unit
    )
}
