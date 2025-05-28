package com.awesomedev.composetest.screener.domain

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface GetInstrumentsUseCase {

    operator fun invoke(): Flow<PagingData<Instrument>>
}

class GetInstrumentsUseCaseImpl @Inject constructor(
    private val screenerRepository: ScreenerRepository
) : GetInstrumentsUseCase {

    override operator fun invoke() =
        screenerRepository.getInstrumentPage()
}