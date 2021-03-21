package com.vdc.assignment.repository.net.repository

import com.vdc.assignment.model.WeatherResponse
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.entities.ResultEntity
import com.vdc.assignment.repository.db.repository.DBRepository
import com.vdc.assignment.repository.net.BaseRepository
import com.vdc.assignment.repository.net.OpenWeatherApi
import com.vdc.assignment.repository.net.Result
import java.util.*
import javax.inject.Inject

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherRepositoryImpl
@Inject constructor(
    private val openWeatherApi: OpenWeatherApi,
    private val db: DBRepository
) : WeatherRepository, BaseRepository() {

    override suspend fun getWeather(searchKey: String, appId: String): Result<WeatherResponse?> {

        val currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        currentCalendar[Calendar.HOUR_OF_DAY] = 0
        currentCalendar[Calendar.MINUTE] = 0
        currentCalendar[Calendar.SECOND] = 0
        currentCalendar[Calendar.MILLISECOND] = 0

        val cachedData = db.getWeatherList(searchKey, currentCalendar.time.time)
        if (!cachedData.isNullOrEmpty()) {
            return Result.success(WeatherResponse(cachedData.map {
                ForecastDataEntity.toForecastData(it)
            }))
        }
        val result = getResult{
            openWeatherApi.getWeather(searchKey = searchKey, appId = appId)
        }
        if (result.status == Result.Status.SUCCESS) {
            db.insertResult(
                ResultEntity(searchKey, currentCalendar.timeInMillis),
                result.data?.forecastData ?: emptyList()
            )
        }
        return result
    }
}
