package com.sumayyah.cryptoprice.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumayyah.cryptoprice.data.CoinDao
import com.sumayyah.cryptoprice.network.CoinApi

class MainViewModelFactory(val coinApi: CoinApi, val coinDao: CoinDao): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(coinApi, coinDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}