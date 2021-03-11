package com.sumayyah.cryptoprice.data

import com.sumayyah.cryptoprice.model.Market

class CoinDao {
    private val coinMap = mutableMapOf<String, Market>()

    fun addCoin(coin: Market) {
        synchronized(coinMap) {
            coinMap[coin.label] = coin
        }
    }

    fun addCoins(coins: List<Market>) {
        synchronized(coinMap) {
            coins.forEach {
                coinMap[it.label] = it
            }
        }
    }

    fun getCoinById(label: String): Market? {
        return coinMap[label]
    }

    fun getAllCoins(): List<Market> {
        return coinMap.values.toList()
    }
}