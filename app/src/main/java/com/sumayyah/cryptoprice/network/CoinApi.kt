package com.sumayyah.cryptoprice.network


import com.sumayyah.cryptoprice.model.MarketsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CoinApi {

    //RXJAVA
    @GET("v2getmarkets")
    fun getCoinListWithRx(): Observable<MarketsResponse>
}