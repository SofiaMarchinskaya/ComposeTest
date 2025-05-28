package com.awesomedev.composetest.screener.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ScreenerRepository {

     fun getInstrumentPage(): Flow<PagingData<Instrument>>

}