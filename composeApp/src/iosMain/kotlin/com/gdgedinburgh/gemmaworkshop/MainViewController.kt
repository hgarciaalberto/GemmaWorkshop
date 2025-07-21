package com.gdgedinburgh.gemmaworkshop

import androidx.compose.ui.window.ComposeUIViewController
import com.gdgedinburgh.gemmaworkshop.di.initKoin
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController { App() }

fun onStartup(llmInferenceDelegate: LLMOperatorSwift) {
    initKoin {
        apply {
            modules(module {
                single {
                    LLMOperatorFactory(llmInferenceDelegate)
                }
            })
        }
    }
}

