package com.sumayyah.cryptoprice.ui.main

import androidx.lifecycle.*
import com.sumayyah.cryptoprice.model.MarketsResponse
import com.sumayyah.cryptoprice.network.CoinApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(val coinApi: CoinApi) : ViewModel(), LifecycleObserver {
    val callStatus = MutableLiveData<ResponseData>()

    val compositeDisposable = CompositeDisposable()

    init {
        callStatus.value = ResponseData(ResponseStatus.LOADING)
        fetchCoinData()
    }

    private fun fetchCoinData() {
        callStatus.value = ResponseData(ResponseStatus.LOADING)

        val disposable = executeApiCall()
            .subscribe({ response ->
                callStatus.value = ResponseData(ResponseStatus.SUCCESS, response)

            }, { e ->
                callStatus.value = ResponseData(ResponseStatus.ERROR, null, e)
            })

        compositeDisposable.add(disposable)
    }

    private fun executeApiCall() : Observable<MarketsResponse> {
        return coinApi.getCoinListWithRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectListener() {
        compositeDisposable.clear()
    }
}

data class ResponseData(val responseStatus: ResponseStatus, val data: MarketsResponse? = null, val error: Throwable? = null)
