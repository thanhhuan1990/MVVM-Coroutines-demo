package com.vdc.assignment.di

import com.vdc.assignment.BuildConfig
import com.vdc.assignment.repository.net.OpenWeatherApi
import com.vdc.assignment.repository.net.repository.WeatherRepository
import com.vdc.assignment.repository.net.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/"

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(retrofit: Retrofit) = retrofit.create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideOpenWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository = repository
}