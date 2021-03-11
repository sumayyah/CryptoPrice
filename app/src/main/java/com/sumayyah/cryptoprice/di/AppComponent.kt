package com.sumayyah.cryptoprice.di

import com.sumayyah.cryptoprice.MainActivity
import com.sumayyah.cryptoprice.MainApplication
import com.sumayyah.cryptoprice.ui.detail.DetailFragment
import com.sumayyah.cryptoprice.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: MainApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(detailFragment: DetailFragment)
}
