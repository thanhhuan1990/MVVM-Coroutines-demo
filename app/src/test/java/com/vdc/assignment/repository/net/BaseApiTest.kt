package com.vdc.assignment.repository.net

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
open class BaseApiTest : BaseRepository() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var service: OpenWeatherApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    protected fun enqueueResponse(filePath: String, responseCode: Int = 200): String {
        val uri = javaClass.classLoader?.getResource(filePath)
        uri?.path?.let {
            val file = File(it)
            val body = String(file.readBytes())
            mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(responseCode))
            return body
        }
        return ""
    }
}