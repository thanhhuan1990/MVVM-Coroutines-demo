package com.vdc.assignment.repository.db.entities

import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class ResultEntityTest {

    @Test
    fun `verify default Constructor`() {
        assertNull(ResultEntity().id)
        assertNull(ResultEntity().searchDate)
        assertNull(ResultEntity().searchKey)
    }
}