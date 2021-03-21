package com.vdc.assignment.model

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class TempTest {

    private val temp = Temp(
        day = 290.0,
        min = 280.0,
        max = 300.0,
        night = 290.0,
        eve = 290.0,
        morn = 290.0
    )

    @Test
    fun `verify Getter`() {
        assertEquals(290.0, temp.day)
        assertEquals(280.0, temp.min)
        assertEquals(300.0, temp.max)
        assertEquals(290.0, temp.night)
        assertEquals(290.0, temp.eve)
        assertEquals(290.0, temp.morn)
    }

    @Test
    fun `verify Setter`() {
        temp.day = 300.0
        assertEquals(300.0, temp.day)
        temp.min = 200.0
        assertEquals(200.0, temp.min)
        temp.max = 350.0
        assertEquals(350.0, temp.max)
        temp.night = 300.0
        assertEquals(300.0, temp.night)
        temp.eve = 280.0
        assertEquals(280.0, temp.eve)
        temp.morn = 270.0
        assertEquals(270.0, temp.morn)
    }

    @Test
    fun `verify default Constructor`() {
        assertEquals(0.0, Temp().day)
        assertEquals(0.0, Temp().min)
        assertEquals(0.0, Temp().max)
        assertEquals(0.0, Temp().night)
        assertEquals(0.0, Temp().eve)
        assertEquals(0.0, Temp().morn)
    }

    @Test
    fun `verify custom Constructor`() {
        assertEquals(290.15, Temp(17).day)
        assertEquals(290.15, Temp(17).min)
        assertEquals(290.15, Temp(17).max)
        assertEquals(290.15, Temp(17).night)
        assertEquals(290.15, Temp(17).eve)
        assertEquals(290.15, Temp(17).morn)
    }

    @Test
    fun `verify function getAvgTemperature`() {
        assertEquals(17, temp.getAvgTemperature())
        assertEquals(-273, Temp(null, null, null, null, null, null).getAvgTemperature())
    }
}