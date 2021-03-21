package com.vdc.assignment.repository

import com.vdc.assignment.model.WeatherResponse


/**
 * Created by Jake.Huynh on 21/03/2021
 *
 * Copyright © 2021 ShopBack. All rights reserved.
 */
interface WeatherRepository {
    suspend fun getWeather(searchKey: String, appId: String): Result<WeatherResponse?>
}