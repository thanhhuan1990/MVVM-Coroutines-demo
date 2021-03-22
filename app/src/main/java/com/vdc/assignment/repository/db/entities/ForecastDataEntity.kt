package com.vdc.assignment.repository.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.model.Temp
import com.vdc.assignment.model.Weather
import com.vdc.assignment.repository.db.DBConstant

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Entity(
        tableName = DBConstant.TABLE_FORECAST,
        foreignKeys = [ForeignKey(
                entity = ResultEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("resultId"),
                onDelete = ForeignKey.CASCADE
        )]
)
data class ForecastDataEntity(
    val resultId: Long,
    val date : Long,
    val temp : Int,
    val pressure : Int,
    val humidity : Int,
    val description : String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    companion object {
        fun fromForecastData(resultId: Long, data: ForecastData): ForecastDataEntity {
            return ForecastDataEntity(
                resultId,
                date = data.date ?: 0,
                temp = data.temp?.getAvgTemperature() ?: 0,
                pressure = data.pressure ?: 0,
                humidity = data.humidity ?: 0,
                description = data.weather?.get(0)?.description ?: ""
            )
        }
        fun toForecastData(data: ForecastDataEntity): ForecastData {
            return ForecastData(
                date = data.date,
                temp = Temp(data.temp),
                pressure = data.pressure,
                humidity = data.humidity,
                weather = listOf(Weather(data.description))
            )
        }
    }
}