package com.gdgedinburgh.gemmaworkshop.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
//    single(named("testApiKey")) { BuildConfig.TEST_API_KEY }
//    factory { Greeting() }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule)
}

fun initKoinIos() = initKoin {}