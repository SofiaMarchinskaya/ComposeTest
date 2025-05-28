package com.awesomedev.composetest.screener.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.awesomedev.composetest.network.api.InstrumentsApi
import com.awesomedev.composetest.screener.domain.ScreenerRepository
import javax.inject.Inject

class ScreenerRepositoryImpl @Inject constructor(
    private val instrumentsApi: InstrumentsApi
) : ScreenerRepository {

    override fun getInstrumentPage() =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 20,
                initialLoadSize = 10,
                maxSize = 100
            ),
            pagingSourceFactory = {
                InstrumentsPagingSource(
                    instrumentsApi = instrumentsApi
                )
            }
        ).flow
}
