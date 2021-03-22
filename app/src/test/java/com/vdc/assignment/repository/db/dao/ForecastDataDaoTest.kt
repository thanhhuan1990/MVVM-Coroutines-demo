package com.vdc.assignment.repository.db.dao

import com.vdc.assignment.repository.db.DbTest
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.entities.ResultEntity
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
    private lateinit var resultDao: ResultDao
    private lateinit var dao: ForecastDataDao

    @Before
    fun setup() {
        super.setUp()
        resultDao = db.resultDao()
        dao = db.weatherDao()
    }

    @Test
    fun `test insert forecast data success`() = runBlocking {
        val resultId = resultDao.insert(ResultEntity("SaiGon", 16160000000))
        val id = dao.insert(ForecastDataEntity(resultId, 16162000000L, 30, 50, 30, ""),)
        assertNotNull(id)
    }

    @Test
    fun `test retries forecast data success`() = runBlocking {
        val resultId = resultDao.insert(ResultEntity("SaiGon", 16160000000))
        dao.insert(ForecastDataEntity(resultId, 16162000000L, 30, 50, 30, ""),)
        val listForecast = dao.findByResultId(resultId)
        assertEquals(1, listForecast.size)
    }
}