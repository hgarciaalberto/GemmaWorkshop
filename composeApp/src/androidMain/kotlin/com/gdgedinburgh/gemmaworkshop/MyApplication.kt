package com.gdgedinburgh.gemmaworkshop

import android.app.Application
import com.gdgedinburgh.gemmaworkshop.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
    }
}