package com.vdc.assignment.repository.net

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Huan.Huynh on 20/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class OpenWeatherApiTest : BaseApiTest() {

    companion object {
        const val SUCCESS = "mock-responses/open_weather_api_success.json"
        const val FAIL_401 = "mock-responses/open_weather_api_fail.json"
        const val FAIL_404 = "mock-responses/open_weather_api_not_found.json"
    }

    /**-------------------------------------------------------------------------------------------*/
    /** region Test OpenWeatherAPI */
    @Test
    fun `test getWeather with success status`() = runBlocking {
        enqueueResponse(SUCCESS)

        val testResult = service.getWeather(searchKey = "saigon", noOfForecastDays = 7, appId = "ABCDEF")

        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/data/2.5/forecast/daily?q=saigon&cnt=7&appid=ABCDEF", request.path)

        assertTrue(testResult.isSuccessful)
        assertEquals(7, testResult.body()?.forecastData?.size)
        assertEquals(1616130000L, testResult.body()?.forecastData?.get(0)?.date)
    }

    @Test
    fun `test getWeather with error status 401`() = runBlocking {
        val errorBody = enqueueResponse(FAIL_401, 401)

        val testResult = service.getWeather(searchKey = "saigon", noOfForecastDays = 7, appId = "ABCDEF")

        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/data/2.5/forecast/daily?q=saigon&cnt=7&appid=ABCDEF", request.path)

        assertFalse(testResult.isSuccessful)
        assertEquals(401, testResult.code())
        assertEquals(errorBody, testResult.errorBody()?.string())
    }

    @Test
    fun `test getWeather with error status 404`() = runBlocking {
        val errorBody = enqueueResponse(FAIL_404, 404)

        val testResult = service.getWeather(searchKey = "saigonasd", noOfForecastDays = 7, appId = "ABCDEF")

        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/data/2.5/forecast/daily?q=saigonasd&cnt=7&appid=ABCDEF", request.path)

        assertFalse(testResult.isSuccessful)
        assertEquals(404, testResult.code())
        assertEquals(errorBody, testResult.errorBody()?.string())
    }
    /** endregion Test OpenWeatherAPI */
    /**-------------------------------------------------------------------------------------------*/
}