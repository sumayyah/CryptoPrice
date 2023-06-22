package com.sumayyah.cryptoprice

import android.app.Application
import com.sumayyah.cryptoprice.di.ComponentHolder
import dagger.internal.DaggerCollections
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.create()
        Timber.plant(Timber.DebugTree())
    }
}