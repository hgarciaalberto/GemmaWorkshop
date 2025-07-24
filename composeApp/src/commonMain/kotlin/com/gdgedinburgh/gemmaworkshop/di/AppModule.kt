package com.gdgedinburgh.gemmaworkshop.di

import com.gdgedinburgh.gemmaworkshop.screens.chat.ChatViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(viewModelsModule)
}

val viewModelsModule = module {
    viewModelOf(::ChatViewModel)
}