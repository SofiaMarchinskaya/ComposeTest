package com.awesomedev.composetest.screener.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.awesomedev.composetest.network.api.InstrumentsApi
import com.awesomedev.composetest.screener.domain.Instrument

class InstrumentsPagingSource(
    private val instrumentsApi: InstrumentsApi,
) : PagingSource<Int, Instrument>() {

    override fun getRefreshKey(state: PagingState<Int, Instrument>) =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Instrument> {
        val page = params.key ?: 1
        return try {
            val response = instrumentsApi.getInstruments(page = page, pageSize = params.loadSize)
            LoadResult.Page(
                data = response.items.map {
                    Instrument(
                        id = it.id,
                        symbol = it.symbol,
                        price = it.price,
                        change = it.change,
                        changePercent = it.changePercent
                    )
                },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}