package com.gdgedinburgh.gemmaworkshop.di

import com.gdgedinburgh.gemmaworkshop.llm.LLMOperator
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperatorFactory
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single<LLMOperator> { get<LLMOperatorFactory>().create() }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule)
}

//fun initKoinIos() = initKoin {}