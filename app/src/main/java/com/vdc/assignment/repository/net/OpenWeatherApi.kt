package com.vdc.assignment.repository.net

import com.vdc.assignment.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
interface OpenWeatherApi {
    @GET("/data/2.5/forecast/daily")
    suspend fun getWeather(@Query("q") searchKey: String,
                           @Query("cnt") noOfForecastDays: Int = 7,
                           @Query("appid") appId: String): Response<WeatherResponse?>
}