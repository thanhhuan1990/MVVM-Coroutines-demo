package com.vdc.assignment.repository.db.repository

import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.repository.db.dao.ResultDao
import com.vdc.assignment.repository.db.dao.ForecastDataDao
import com.vdc.assignment.repository.db.entities.ResultEntity
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import javax.inject.Inject

/**
 * Created by Huan.Huynh on 19/11/2020
 *
 * Copyright © 2020 ShopBack. All rights reserved.
 */
class DBRepositoryImpl @Inject constructor(
    private val resultDao: ResultDao,
    private val weatherDao: ForecastDataDao
): DBRepository {

    override suspend fun insertResult(resultEntity: ResultEntity, data: List<ForecastData>): Long {
        val resultId = resultDao.insert(resultEntity)
        data.map { forecastData ->
            weatherDao.insert(ForecastDataEntity.fromForecastData(resultId, forecastData))
        }
        return resultId
    }

    override suspend fun getWeatherList(searchKey: String, date: Long): List<ForecastDataEntity>? {
        val result = resultDao.findResult(searchKey, date)
        if (!result.isNullOrEmpty() && result[0].id != null) {
            result[0].id?.let {
                return weatherDao.findByResultId(it)
            }
        }
        return null
    }
}