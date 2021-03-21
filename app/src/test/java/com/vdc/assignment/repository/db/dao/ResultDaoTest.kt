package com.vdc.assignment.repository.db.dao

import com.vdc.assignment.repository.db.DbTest
import com.vdc.assignment.repository.db.entities.ResultEntity
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Huan.Huynh on 20/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@ExperimentalCoroutinesApi
class ResultDaoTest : DbTest()  {
    private lateinit var dao: ResultDao

    @Before
    fun setup() {
        super.setUp()
        dao = db.resultDao()
    }

    @Test
    fun `test insert forecast data success`() = runBlocking {
        val id = dao.insert(ResultEntity("SaiGon", 16160000000))
        assertNotNull(id)
    }

    @Test
    fun `test retries forecast data success`() = runBlocking {
        dao.insert(ResultEntity("SaiGon", 16160000000))
        val listForecast = dao.findResult("SaiGon", 16160000000)
        assertEquals(1, listForecast.size)
    }
}