package com.xfinity.xhome.cryptoprice.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xfinity.xhome.cryptoprice.network.CoinApi

class MainViewModelFactory(val coinApi: CoinApi): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(coinApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}