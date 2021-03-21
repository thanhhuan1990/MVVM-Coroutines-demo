package com.vdc.assignment.repository.db.entities

import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.model.Temp
import com.vdc.assignment.model.Weather
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class ForecastDataEntityTest {

    @Test
    fun `verify companion fromForecastData`() {
         // Given
        val temp = Temp(300.0,300.0,300.0,300.0,300.0, 300.0)
        val weather = Weather(description = "Light rain")
        val forecastData = ForecastData(
            date = 1616202000L,
            temp = temp,
            pressure = 30,
            humidity = 50,
            weather = listOf(weather)
        )

        // When
        val forecastDataEntity = ForecastDataEntity.fromForecastData(1, forecastData)

        // Then
        assertEquals(1616202000L, forecastDataEntity.date)
        assertEquals(27, forecastDataEntity.temp)
        assertEquals(30, forecastDataEntity.pressure)
        assertEquals(50, forecastDataEntity.humidity)
        assertEquals(weather.description, forecastDataEntity.description)
    }

    @Test
    fun `verify companion fromForecastData with default value`() {
        // Given
        val weather = Weather(description = "Light rain")
        val forecastData = ForecastData(
            date = null,
            temp = null,
            pressure = null,
            humidity = null,
            weather = null
        )

        // When
        val forecastDataEntity = ForecastDataEntity.fromForecastData(1, forecastData)

        // Then
        assertEquals(0, forecastDataEntity.date)
        assertEquals(0, forecastDataEntity.temp)
        assertEquals(0, forecastDataEntity.pressure)
        assertEquals(0, forecastDataEntity.humidity)
        assertEquals("", forecastDataEntity.description)
    }
}