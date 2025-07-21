package com.gdgedinburgh.gemmaworkshop.llm

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Startup {
    fun run(platformSpecifiedKoinInitBlock: (koin: KoinApplication) -> Unit) {
        startKoin {
            modules(sharedModule)
            platformSpecifiedKoinInitBlock(this)
        }
    }
}

val sharedModule = module {
    single<LLMOperator> { get<LLMOperatorFactory>().create() }
}