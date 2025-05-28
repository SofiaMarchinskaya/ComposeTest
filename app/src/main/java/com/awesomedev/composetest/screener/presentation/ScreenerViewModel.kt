package com.awesomedev.composetest.screener.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.awesomedev.composetest.screener.domain.GetInstrumentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScreenerViewModel @Inject constructor(
    getInstruments: GetInstrumentsUseCase
) : ViewModel() {

    val instrumentsPagingFlow = getInstruments().cachedIn(viewModelScope)

}