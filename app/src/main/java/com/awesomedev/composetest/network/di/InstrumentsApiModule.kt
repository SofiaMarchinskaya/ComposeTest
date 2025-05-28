package com.awesomedev.composetest.network.di

import com.awesomedev.composetest.network.api.InstrumentsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class InstrumentsApiModule {

    @Singleton
    @Provides
    fun provideDogApi(retrofit: Retrofit): InstrumentsApi =
        retrofit.create(InstrumentsApi::class.java)
}