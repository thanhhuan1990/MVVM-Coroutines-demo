package com.vdc.assignment.di

import android.content.Context
import com.vdc.assignment.repository.db.AppDatabase
import com.vdc.assignment.repository.db.DBConstant
import com.vdc.assignment.repository.db.dao.ResultDao
import com.vdc.assignment.repository.db.dao.ForecastDataDao
import com.vdc.assignment.repository.db.repository.DBRepository
import com.vdc.assignment.repository.db.repository.DBRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    @Named("DB_NAME")
    fun provideDatabaseName(): String = DBConstant.DB_NAME

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named("DB_NAME") dbName: String
    ): AppDatabase {
        return AppDatabase.getInstance(context, dbName)
    }

    @Provides
    fun provideResultDao(appDatabase: AppDatabase): ResultDao = appDatabase.resultDao()

    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): ForecastDataDao = appDatabase.weatherDao()

    @Provides
    fun provideDBRepository(appDatabase: AppDatabase): DBRepository = DBRepositoryImpl(appDatabase.resultDao(), appDatabase.weatherDao())
}