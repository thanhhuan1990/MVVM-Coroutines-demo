package com.vdc.assignment.repository.db.repository

import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.entities.ResultEntity

/**
 * Created by Huan.Huynh on 20/11/2020
 *
 * Copyright © 2020 ShopBack. All rights reserved.
 */
interface DBRepository {
    suspend fun insertResult(resultEntity: ResultEntity, data: List<ForecastData>): Long
    suspend fun getWeatherList(searchKey: String, date: Long): List<ForecastDataEntity>?
}