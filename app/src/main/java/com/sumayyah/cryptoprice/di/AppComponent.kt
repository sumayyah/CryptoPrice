package com.sumayyah.cryptoprice.di

import com.sumayyah.cryptoprice.MainActivity
import com.sumayyah.cryptoprice.MainApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: MainApplication)
    fun inject(mainActivity: MainActivity)
}
