package com.gdgedinburgh.gemmaworkshop

import android.app.Application
import com.gdgedinburgh.gemmaworkshop.di.initKoin
import com.gdgedinburgh.gemmaworkshop.llm.LLMOperatorFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(androidModule)
            androidLogger()
            androidContext(this@MyApplication)
        }
    }
}

val androidModule = module {
    single {
        LLMOperatorFactory().apply {
            initialize(androidContext())
            create()
        }
    }
}