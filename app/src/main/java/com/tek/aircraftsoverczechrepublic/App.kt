package com.tek.aircraftsoverczechrepublic

import android.app.Application
import com.tek.aircraftsoverczechrepublic.di.networkModule
import com.tek.aircraftsoverczechrepublic.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}