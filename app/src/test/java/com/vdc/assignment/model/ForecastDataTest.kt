package com.vdc.assignment.model

import android.content.Context
import com.nhaarman.mockitokotlin2.whenever
import com.vdc.assignment.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class ForecastDataTest {

    @Mock
    lateinit var context: Context

    private val temp = Temp(1.0,2.0,3.0,4.0,5.0)
    private val weather = Weather(description = "Light rain")
    private val forecastData = ForecastData(
            date = 1616202000L,
            temp = temp,
            pressure = 30,
            humidity = 50,
            weather = listOf(weather)
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `verify Getter`() {
        assertEquals(1616202000L, forecastData.date)
        assertEquals(temp, forecastData.temp)
        assertEquals(30, forecastData.pressure)
        assertEquals(50, forecastData.humidity)
        assertEquals(weather, forecastData.weather?.get(0))
    }

    @Test
    fun `verify Default constructor`() {
        assertEquals(0L, ForecastData().date)
        assertNull(ForecastData().temp)
        assertEquals(0, ForecastData().pressure)
        assertEquals(0, ForecastData().humidity)
        assertEquals(0, ForecastData().weather?.size)
    }

    @Test
    fun `verify getDisplayDate`() {
        assertEquals("Sat, 20 Mar 2021", forecastData.getDisplayDate())
        assertEquals("Thu, 01 Jan 1970", ForecastData(null, null, null, null, null).getDisplayDate())
    }

    @Test
    fun `verify toString`() {
        val placeHolderContent = "Date: %s, Average temperature: %d degrees celsius, pressure: %d, humidity: %d%%, Description: %s"

        whenever(context.getString(R.string.talkBack_forecast)).thenReturn(placeHolderContent)

        assertEquals(String.format(placeHolderContent, forecastData.getDisplayDate(),
            forecastData.temp?.getAvgTemperature(), forecastData.pressure, forecastData.humidity, forecastData.weather?.get(0)?.description
        ), forecastData.toString(context))
    }
}