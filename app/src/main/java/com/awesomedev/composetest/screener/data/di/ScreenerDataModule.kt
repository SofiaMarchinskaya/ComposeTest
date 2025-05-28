package com.awesomedev.composetest.screener.data.di

import com.awesomedev.composetest.screener.data.ScreenerRepositoryImpl
import com.awesomedev.composetest.screener.domain.ScreenerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ScreenerDataModule {

    @Singleton
    @Binds
    fun bindScreenerRepository(impl: ScreenerRepositoryImpl): ScreenerRepository
}