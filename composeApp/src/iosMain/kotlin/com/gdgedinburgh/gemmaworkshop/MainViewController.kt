package com.gdgedinburgh.gemmaworkshop

import androidx.compose.ui.window.ComposeUIViewController
import com.gdgedinburgh.gemmaworkshop.di.initKoin
import com.gdgedinburgh.gemmaworkshop.di.viewModelsModule
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperator
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperatorFactory
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperatorSwift
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController { App() }

fun onStartup(llmInferenceDelegate: LLMOperatorSwift) {
    initKoin {
        apply {
            modules(
                module {
                    single<LLMOperator> {
                        LLMOperatorFactory().run {
                            initialize(llmInferenceDelegate)
                            create()
                        }
                    }
                },
                viewModelsModule
            )
        }
    }
}
