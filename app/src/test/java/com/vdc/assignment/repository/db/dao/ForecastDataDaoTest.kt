package com.vdc.assignment.repository.db.dao

import com.vdc.assignment.repository.db.DbTest
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Huan.Huynh on 20/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@ExperimentalCoroutinesApi
class ForecastDataDaoTest : DbTest()  {
    private lateinit var dao: ForecastDataDao

    @Before
    fun setup() {
        super.setUp()
        dao = db.weatherDao()
    }

    @Test
    fun `test insert forecast data success`() = runBlocking {
        val id = dao.insert(ForecastDataEntity(1, 16162000000L, 30, 50, 30, ""),)
        assertNotNull(id)
    }

    @Test
    fun `test retries forecast data success`() = runBlocking {
        dao.insert(ForecastDataEntity(1, 16162000000L, 30, 50, 30, ""),)
        val listForecast = dao.findByResultId(1)
        assertEquals(1, listForecast.size)
    }
}