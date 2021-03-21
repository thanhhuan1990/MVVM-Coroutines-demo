package com.vdc.assignment.repository.net.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.vdc.assignment.model.*
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.repository.DBRepository
import com.vdc.assignment.repository.net.BaseRepository
import com.vdc.assignment.repository.net.OpenWeatherApi
import com.vdc.assignment.repository.net.OpenWeatherApiTest
import com.vdc.assignment.repository.net.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.File

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest : BaseRepository() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: OpenWeatherApi

    @Mock
    lateinit var db: DBRepository

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
        repository = spy(WeatherRepositoryImpl(api, db))
    }

    @Test
    fun `test result Success of getWeather from remote`() = runBlocking {
        // Given
        whenever(db.getWeatherList(any(), any())).thenReturn(null)
        whenever(api.getWeather(any(), any(), any())).thenReturn(Response.success(weatherResponse))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        // Then
        assertEquals(Result.Status.SUCCESS, result.status)
        assertEquals(weatherResponse, result.data)
    }

    @Test
    fun `test result Fail of getWeather from remote`() = runBlocking {
        // Given
        val uri = javaClass.classLoader?.getResource(OpenWeatherApiTest.FAIL_404)
        var body: String? = null
        uri?.path?.let {
            val file = File(it)
            body = String(file.readBytes())
        }
        whenever(api.getWeather(any(), any(), any()))
            .thenReturn(Response.error(404, body?.toResponseBody()!!))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        assertEquals(Result.Status.ERROR, result.status)
        assertEquals("404", (result.exception as OpenWeatherException).errorData.cod)
        assertEquals("city not found",
            (result.exception as OpenWeatherException).errorData.message)
    }

    @Test
    fun `test result Fail of getWeather from remote un-parsable`() = runBlocking {
        // Given
        whenever(api.getWeather(any(), any(), any()))
            .thenReturn(Response.error(404, "".toResponseBody()))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        assertEquals(Result.Status.ERROR, result.status)
        assertEquals("Response.error()", result.exception?.message)
    }

    @Test
    fun `test result Fail of getWeather from remote exception`() = runBlocking {
        // Given
        whenever(api.getWeather(any(), any(), any()))
            .thenReturn(Response.error(404, "{}}".toResponseBody()))

        // When
        val result = repository.getWeather("saigon",  "ABCDEF")

        assertEquals(Result.Status.ERROR, result.status)
        assertTrue(
            result.exception?.message?.contains("com.google.gson.stream.MalformedJsonException") ?: false)
    }

    @Test
    fun `test result Success of getWeather from local`() = runBlocking {
        // Given
        whenever(db.getWeatherList(any(), any())).thenReturn(listOf(
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
        assertEquals(Result.Status.SUCCESS, result.status)
        assertEquals(7, result.data?.forecastData?.size)
        assertEquals(16162000000L, result.data?.forecastData?.get(0)?.date)
        assertEquals(303.15, result.data?.forecastData?.get(0)?.temp?.day)
    }
}