package com.vdc.assignment.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.vdc.assignment.model.*
import com.vdc.assignment.repository.net.Result
import com.vdc.assignment.repository.net.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@ExperimentalCoroutinesApi
class WeatherListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: WeatherRepository

    private lateinit var viewmodel: WeatherListViewModel

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

        viewmodel = spy(WeatherListViewModel(repository))

    }

    @Test
    fun `verify function searchWeather success`() = runBlocking {
        // Given
        whenever(repository.getWeather(any(), any())).thenReturn(Result.success(weatherResponse))

        // When
        val observer = mock<Observer<List<ForecastData?>>>()
        viewmodel.result.observeForever(observer)
        viewmodel.searchWeather("")

        // Then
        Mockito.verify(observer).onChanged(weatherResponse.forecastData)
    }

    @Test
    fun `verify function searchWeather success with null data`() = runBlocking {
        // Given
        whenever(repository.getWeather(any(), any())).thenReturn(Result.success(WeatherResponse(null)))

        // When
        val observer = mock<Observer<List<ForecastData?>>>()
        viewmodel.result.observeForever(observer)
        viewmodel.searchWeather("")

        // Then
        Mockito.verify(observer).onChanged(emptyList())
    }

    @Test
    fun `verify function searchWeather fail`() = runBlocking {
        // Given
        val fakeException = OpenWeatherException(ErrorData("404", "city not found"))
        whenever(repository.getWeather(any(), any()))
            .thenReturn(Result.error(fakeException))

        // When
        val observer = mock<Observer<List<ForecastData?>>>()
        val observerError = mock<Observer<Exception?>>()
        viewmodel.error.observeForever(observerError)
        viewmodel.result.observeForever(observer)
        viewmodel.searchWeather("")

        // Then
        verify(observer).onChanged(emptyList())
        verify(observerError).onChanged(fakeException)
    }
}
