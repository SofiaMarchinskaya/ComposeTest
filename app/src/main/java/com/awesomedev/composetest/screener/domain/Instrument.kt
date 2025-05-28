package com.awesomedev.composetest.screener.domain

data class Instrument(
    val id: Int,
    val symbol: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
)
