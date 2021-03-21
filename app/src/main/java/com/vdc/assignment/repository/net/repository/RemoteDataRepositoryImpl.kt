package com.vdc.assignment.repository.net.repository

import com.vdc.assignment.model.WeatherResponse
import com.vdc.assignment.repository.Result
import com.vdc.assignment.repository.net.BaseRepository
import com.vdc.assignment.repository.net.OpenWeatherApi
import javax.inject.Inject

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class RemoteDataRepositoryImpl
@Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : RemoteDataRepository, BaseRepository() {

    override suspend fun getWeather(searchKey: String, appId: String): Result<WeatherResponse?> {
        return getResult{
            openWeatherApi.getWeather(searchKey = searchKey, appId = appId)
        }
    }
}
