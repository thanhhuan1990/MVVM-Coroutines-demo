package com.vdc.assignment.di

import com.vdc.assignment.BuildConfig
import com.vdc.assignment.repository.WeatherRepository
import com.vdc.assignment.repository.WeatherRepositoryImpl
import com.vdc.assignment.repository.net.OpenWeatherApi
import com.vdc.assignment.repository.net.repository.RemoteDataRepository
import com.vdc.assignment.repository.net.repository.RemoteDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
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
    fun provideOkHttpClient(BASE_URL: String): OkHttpClient {
        val certificate = CertificatePinner.Builder()
                .add("api.openweathermap.org", "sha256/axmGTWYycVN5oCjh3GJrxWVndLSZjypDO6evrHMwbXg=")
                .add("api.openweathermap.org", "sha256/4a6cPehI7OG6cuDZka5NDZ7FR8a60d3auda+sKfg4Ng=")
                .add("api.openweathermap.org", "sha256/x4QzPSC810K5/cMjb05Qm4k3Bw5zBn4lTdO/nEW/Td4=")
                .build()

        return if (BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .certificatePinner(certificate)
                    .build()
        } else {
            OkHttpClient
                    .Builder()
                    .certificatePinner(certificate)
                    .build()
        }
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
    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideOpenWeatherRepository(repository: RemoteDataRepositoryImpl): RemoteDataRepository = repository

    @Provides
    @Singleton
    fun provideWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository = repository
}