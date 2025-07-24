package com.gdgedinburgh.gemmaworkshop

import android.app.Application
import com.gdgedinburgh.gemmaworkshop.di.initKoin
import com.gdgedinburgh.gemmaworkshop.di.viewModelsModule
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperator
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperatorFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(androidModule, viewModelsModule)
        }
    }
}

val androidModule = module {
    single<LLMOperator> {
        LLMOperatorFactory().run {
            initialize(androidContext())
            create()
        }
    }
}