package com.vdc.assignment.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.vdc.assignment.model.*
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.repository.DBRepository
import com.vdc.assignment.repository.net.repository.RemoteDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Jake.Huynh on 21/03/2021
 *
 * Copyright © 2021 ShopBack. All rights reserved.
 */
@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var remote: RemoteDataRepository

    @Mock
    lateinit var local: DBRepository

    private lateinit var repository: WeatherRepository

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

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = spy(WeatherRepositoryImpl(remote, local))
    }

    @Test
    fun `test result Success of getWeather from remote`() = runBlocking {
        // Given
        whenever(local.getWeatherList(any(), any())).thenReturn(null)
        whenever(remote.getWeather(any(), any())).thenReturn(Result.success(weatherResponse))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        // Then
        Assert.assertEquals(Result.Status.SUCCESS, result.status)
        Assert.assertEquals(weatherResponse, result.data)
    }

    @Test
    fun `test result Fail of getWeather from remote`() = runBlocking {
        // Given
        whenever(local.getWeatherList(any(), any())).thenReturn(null)
        whenever(remote.getWeather(any(), any()))
            .thenReturn(Result.error(OpenWeatherException(ErrorData("404", "city not found"))))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        Assert.assertEquals(Result.Status.ERROR, result.status)
        Assert.assertEquals("404", (result.exception as OpenWeatherException).errorData.cod)
        Assert.assertEquals("city not found",
            (result.exception as OpenWeatherException).errorData.message)
    }

    @Test
    fun `test result Success of getWeather from local`() = runBlocking {
        // Given
        whenever(local.getWeatherList(any(), any())).thenReturn(listOf(
            ForecastDataEntity(1, 16162000000L, 30, 50, 30, ""),
            ForecastDataEntity(1, 16164000000L, 30, 50, 30, ""),
            ForecastDataEntity(1, 16165000000L, 30, 50, 30, ""),
            ForecastDataEntity(1, 16166000000L, 30, 50, 30, ""),
            ForecastDataEntity(1, 16167000000L, 30, 50, 30, ""),
            ForecastDataEntity(1, 16168000000L, 30, 50, 30, ""),
            ForecastDataEntity(1, 16169000000L, 30, 50, 30, ""),
        ))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        // Then
        Assert.assertEquals(Result.Status.SUCCESS, result.status)
        Assert.assertEquals(7, result.data?.forecastData?.size)
        Assert.assertEquals(16162000000L, result.data?.forecastData?.get(0)?.date)
        Assert.assertEquals(303.15, result.data?.forecastData?.get(0)?.temp?.day)
    }
}