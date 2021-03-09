package com.sumayyah.cryptoprice.ui.main

import androidx.lifecycle.*
import com.sumayyah.cryptoprice.model.MarketsResponse
import com.sumayyah.cryptoprice.network.CoinApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(val coinApi: CoinApi) : ViewModel(), LifecycleObserver {
    val callStatus = MutableLiveData<ResponseData>()

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
                callStatus.postValue(ResponseData(ResponseStatus.SUCCESS, response))
            } catch (e: Exception) {
                callStatus.postValue(ResponseData(ResponseStatus.ERROR, null, e))
            }
        }
    }

    private suspend fun executeApiCall() : MarketsResponse {
        return coinApi.getCoinListWithRx()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectListener() {
        job.cancel()
    }
}

data class ResponseData(val responseStatus: ResponseStatus, val data: MarketsResponse? = null, val error: Throwable? = null)
