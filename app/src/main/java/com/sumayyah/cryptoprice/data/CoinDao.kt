package com.sumayyah.cryptoprice.data

import com.sumayyah.cryptoprice.model.Market

class CoinDao {
    private val coinMap = mutableMapOf<String, Market>()

    fun addCoin(coin: Market) {
        synchronized(coinMap) {
            coinMap[coin.label] = coin
        }
    }

    fun getCoinById(label: String): Market? {
        return coinMap[label]
    }

    fun getAllCoins(): List<Market> {
        return coinMap.values.toList()
    }
}