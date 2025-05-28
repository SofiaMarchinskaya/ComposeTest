package com.awesomedev.composetest.network.api

import com.awesomedev.composetest.network.api.models.InstrumentsPagedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface InstrumentsApi {

    @GET("instruments")
    suspend fun getInstruments(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): InstrumentsPagedDto
}