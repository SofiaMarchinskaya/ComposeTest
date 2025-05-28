package com.awesomedev.composetest.network.api.models

import com.google.gson.annotations.SerializedName

data class InstrumentsPagedDto(
    @SerializedName("items")
    val items: List<InstrumentDto>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("totalItems")
    val totalItems: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
)
