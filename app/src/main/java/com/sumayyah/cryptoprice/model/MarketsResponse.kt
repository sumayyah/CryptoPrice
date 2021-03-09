package com.sumayyah.cryptoprice.model

import com.google.gson.annotations.SerializedName

data class MarketsResponse(
    @SerializedName("Markets") val markets : List<List<Market>>
)
