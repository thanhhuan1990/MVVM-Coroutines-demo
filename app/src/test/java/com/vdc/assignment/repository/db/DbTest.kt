package com.vdc.assignment.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [21])
abstract class DbTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var _db: AppDatabase
    val db: AppDatabase
        get() = _db

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        _db.close()
    }
}