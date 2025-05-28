package com.awesomedev.composetest.network.api.models

import com.google.gson.annotations.SerializedName

data class InstrumentDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("change")
    val change: Double,
    @SerializedName("changePercent")
    val changePercent: Double,
)