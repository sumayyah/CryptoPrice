package com.sumayyah.cryptoprice.ui.main

import androidx.lifecycle.*
import com.sumayyah.cryptoprice.data.CoinDao
import com.sumayyah.cryptoprice.model.Market
import com.sumayyah.cryptoprice.model.MarketsResponse
import com.sumayyah.cryptoprice.network.CoinApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val coinApi: CoinApi, private val coinDao: CoinDao) : ViewModel(), LifecycleObserver {
    val callStatus = MutableLiveData<ResponseData>()
    val currentCoinList = MutableLiveData<List<Market>>()

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.IO)

    init {
        callStatus.value = ResponseData(ResponseStatus.LOADING)
        fetchCoinData()
    }

    private fun fetchCoinData() {
        callStatus.value = ResponseData(ResponseStatus.LOADING)

        scope.launch {
            try {
                val response = executeApiCall()
                updateDao(response)

                callStatus.postValue(ResponseData(ResponseStatus.SUCCESS))
                currentCoinList.postValue(coinDao.getAllCoins())

            } catch (e: Exception) {
                callStatus.postValue(ResponseData(ResponseStatus.ERROR, e))
            }
        }
    }

    private fun updateDao(response: MarketsResponse) {
        coinDao.addCoins(response.markets[0].subList(0, 20))
    }

    private suspend fun executeApiCall() : MarketsResponse {
        return coinApi.getCoinListWithRx()
    }

    fun getCoin(label: String) : Market? {
        return coinDao.getCoinById(label)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectListener() {
        job.cancel()
    }
}

data class ResponseData(val responseStatus: ResponseStatus, val error: Throwable? = null)
