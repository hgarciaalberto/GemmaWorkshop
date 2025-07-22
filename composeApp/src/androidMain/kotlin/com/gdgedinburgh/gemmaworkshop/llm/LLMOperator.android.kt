package com.gdgedinburgh.gemmaworkshop.llm

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

const val ANDROID_MODEL_FOLDER = "data/local/tmp/llm/"

@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class LLMOperatorFactory {

    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context
    }

    actual fun create(): LLMOperator {
        if (!::context.isInitialized) {
            throw IllegalStateException("LLMOperatorFactory not initialized")
        }
        return LLMInferenceAndroidImpl(context)
    }
}

class LLMInferenceAndroidImpl(private val context: Context) : LLMOperator {

    private lateinit var llmInference: LlmInference
    private val initialized = AtomicBoolean(false)
    private val partialResultsFlow = MutableSharedFlow<Pair<String, Boolean>>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun initModel(): String? {
        if (initialized.get()) {
            return null
        }
        return try {
            val modelPath = "$ANDROID_MODEL_FOLDER$MODEL_NAME.$MODEL_EXTENSION"
            if (File(modelPath).exists().not()) {
                return "Model not found at path: $modelPath"
            }
            loadModel(modelPath)
            initialized.set(true)
            null
        } catch (e: Exception) {
            e.message
        }
    }

    private fun loadModel(modelPath: String) {
        val options = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(modelPath)
            .setMaxTokens(1024)
//            .setResultListener { partialResult, done ->
//                // This API design is weird...
//                // We need to define the result listener in LlmInferenceOptions,
//                // and later use it with LlmInference#generateResponseAsync(...)
//                partialResultsFlow.tryEmit(partialResult to done)
//            }
            .build()

        llmInference = LlmInference.createFromOptions(context, options)
    }

    override fun sizeInTokens(text: String): Int = llmInference.sizeInTokens(text)

    override suspend fun generateResponse(inputText: String): String {
        if (initialized.get().not()) {
            throw IllegalStateException("LLMInference is not initialized properly")
        }
        return llmInference.generateResponse(inputText)
    }

    override suspend fun generateResponseAsync(inputText: String): Flow<Pair<String, Boolean>> {
        if (initialized.get().not()) {
            throw IllegalStateException("LLMInference is not initialized properly")
        }
        llmInference.generateResponseAsync(inputText)
        return partialResultsFlow.asSharedFlow()
    }
}