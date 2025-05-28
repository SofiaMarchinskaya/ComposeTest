package com.awesomedev.composetest.screener.domain.di

import com.awesomedev.composetest.screener.domain.GetInstrumentsUseCase
import com.awesomedev.composetest.screener.domain.GetInstrumentsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ScreenerDomainModule {

    @Singleton
    @Binds
    fun bindGetInstrumentsUseCase(impl: GetInstrumentsUseCaseImpl): GetInstrumentsUseCase
}