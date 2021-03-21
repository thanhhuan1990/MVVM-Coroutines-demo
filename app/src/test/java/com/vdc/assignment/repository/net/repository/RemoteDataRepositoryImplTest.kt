package com.vdc.assignment.repository.net.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.vdc.assignment.model.*
import com.vdc.assignment.repository.Result
import com.vdc.assignment.repository.net.BaseRepository
import com.vdc.assignment.repository.net.OpenWeatherApi
import com.vdc.assignment.repository.net.OpenWeatherApiTest
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
class RemoteDataRepositoryImplTest : BaseRepository() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: OpenWeatherApi

    private lateinit var repository: RemoteDataRepository

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
        repository = spy(RemoteDataRepositoryImpl(api))
    }

    @Test
    fun `test result Success of getWeather from remote`() = runBlocking {
        // Given
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
}
