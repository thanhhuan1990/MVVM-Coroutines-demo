package com.vdc.assignment.repository.net.repository

import com.vdc.assignment.model.WeatherResponse
import com.vdc.assignment.repository.Result


/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
interface RemoteDataRepository {
    suspend fun getWeather(searchKey: String, appId: String): Result<WeatherResponse?>
}