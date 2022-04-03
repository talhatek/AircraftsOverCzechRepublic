package com.tek.aircraftsoverczechrepublic

import android.app.Application
import com.tek.aircraftsoverczechrepublic.di.testNetworkModule
import com.tek.aircraftsoverczechrepublic.di.testViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.NONE)
            androidContext(this@TestApp)
            modules(modules = listOf(testViewModelModule,testNetworkModule))

        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}