package com.sumayyah.cryptoprice.model

import com.google.gson.annotations.SerializedName

data class Market (
    @SerializedName("Label") val label : String,
    @SerializedName("Name") val name : String,
    @SerializedName("Price") val price : Double,
    @SerializedName("Volume_24h") val volume_24h : Double,
    @SerializedName("Timestamp") val timestamp : Int
)