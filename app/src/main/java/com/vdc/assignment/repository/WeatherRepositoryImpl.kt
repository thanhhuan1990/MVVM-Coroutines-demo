package com.vdc.assignment.repository

import com.vdc.assignment.model.WeatherResponse
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.entities.ResultEntity
import com.vdc.assignment.repository.db.repository.DBRepository
import com.vdc.assignment.repository.net.BaseRepository
import com.vdc.assignment.repository.net.repository.RemoteDataRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Jake.Huynh on 21/03/2021
 *
 * Copyright © 2021 ShopBack. All rights reserved.
 */
class WeatherRepositoryImpl
@Inject constructor(
        private val remote: RemoteDataRepository,
        private val local: DBRepository
): WeatherRepository, BaseRepository() {

    override suspend fun getWeather(searchKey: String, appId: String): Result<WeatherResponse?> {
        val currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        currentCalendar[Calendar.HOUR_OF_DAY] = 0
        currentCalendar[Calendar.MINUTE] = 0
        currentCalendar[Calendar.SECOND] = 0
        currentCalendar[Calendar.MILLISECOND] = 0

        val cachedData = local.getWeatherList(searchKey, currentCalendar.time.time)
        if (!cachedData.isNullOrEmpty()) {
            return Result.success(WeatherResponse(cachedData.map {
                ForecastDataEntity.toForecastData(it)
            }))
        }
        val result = remote.getWeather(searchKey, appId)
        if (result.status == Result.Status.SUCCESS) {
            local.insertResult(
                ResultEntity(searchKey, currentCalendar.timeInMillis),
                result.data?.forecastData ?: emptyList()
            )
        }
        return result
    }
}