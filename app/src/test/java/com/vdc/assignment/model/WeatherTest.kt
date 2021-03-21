package com.vdc.assignment.model

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherTest {
    private val weather = Weather("Light rain")

    @Test
    fun `verify Constructor`() {
        assertEquals("Light rain", weather.description)
    }

    @Test
    fun `verify default Constructor`() {
        assertEquals("", Weather().description)
    }
}