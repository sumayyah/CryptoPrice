package com.sumayyah.cryptoprice

import android.app.Application
import com.sumayyah.cryptoprice.di.AppComponent
import com.sumayyah.cryptoprice.di.AppModule
import com.sumayyah.cryptoprice.di.DaggerAppComponent
import timber.log.Timber

class MainApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

        Timber.plant(Timber.DebugTree())
    }
}