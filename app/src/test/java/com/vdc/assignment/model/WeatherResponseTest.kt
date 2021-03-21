package com.vdc.assignment.model

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherResponseTest {

    private val temp = Temp(1.0,2.0,3.0,4.0,5.0)
    private val weather = Weather(description = "Light rain")
    private val weatherResponse = WeatherResponse(
        forecastData = listOf(
            ForecastData(
                date = 1616202000L,
                temp = temp,
                pressure = 30,
                humidity = 50,
                weather = listOf(weather)
            ),
            ForecastData(
                date = 1616202001L,
                temp = temp,
                pressure = 30,
                humidity = 50,
                weather = listOf(weather)
            ),
            ForecastData(
                date = 1616202001L,
                temp = temp,
                pressure = 30,
                humidity = 50,
                weather = listOf(weather)
            )
        )
    )

    @Test
    fun `verify Constructor`() {
        assertEquals(3, weatherResponse.forecastData?.size)
        assertEquals(1616202001L, weatherResponse.forecastData?.get(1)?.date)
    }

    @Test
    fun `verify default Constructor`() {
        assertEquals(0, WeatherResponse().forecastData?.size)
    }
}