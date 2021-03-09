package com.sumayyah.cryptoprice.network


import com.sumayyah.cryptoprice.model.MarketsResponse
import retrofit2.http.GET

interface CoinApi {

    @GET("v2getmarkets")
    suspend fun getCoinListWithRx(): MarketsResponse
}