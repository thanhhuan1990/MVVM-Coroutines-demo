package com.vdc.assignment.repository.db.repository

import android.os.Build
import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.model.Temp
import com.vdc.assignment.model.Weather
import com.vdc.assignment.repository.db.DbTest
import com.vdc.assignment.repository.db.dao.ForecastDataDao
import com.vdc.assignment.repository.db.dao.ResultDao
import com.vdc.assignment.repository.db.entities.ResultEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class DBRepositoryImplTest : DbTest() {

    lateinit var resultDao: ResultDao
    lateinit var forecastDataDao: ForecastDataDao

    private lateinit var recordRepo: DBRepository

    @Before
    fun setup() {
        super.setUp()

        resultDao = db.resultDao()
        forecastDataDao = db.weatherDao()

        recordRepo = Mockito.spy(DBRepositoryImpl(db.resultDao(), db.weatherDao()))
    }

    @Test
    fun `verify insertResult`() = runBlocking {
        val id = recordRepo.insertResult(
            ResultEntity("SaiGon", 16161000000),
            emptyList()
        )
        assertNotNull(id)
    }

    @Test
    fun `verify getWeatherList`() = runBlocking {
        val temp = Temp(1.0,2.0,3.0,4.0,5.0)
        val weather = Weather(description = "Light rain")
        recordRepo.insertResult(
            ResultEntity("SaiGon", 16161000000),
            listOf(
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
        val forecastList = recordRepo.getWeatherList("SaiGon", 16161000000)
        assertEquals(3, forecastList?.size)
        assertEquals(1616202000L, forecastList?.get(0)?.date)
    }

    @Test
    fun `verify getWeatherList return empty`() = runBlocking {
        val forecastList = recordRepo.getWeatherList("SaiGon", 16161000000)
        assertNull(forecastList)
    }
}
