package com.vdc.assignment.repository.net

import com.google.gson.GsonBuilder
import com.vdc.assignment.model.ErrorData
import com.vdc.assignment.model.OpenWeatherException
import retrofit2.Response


/**
 * Created by Huan.Huynh on 19/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
abstract class BaseRepository {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.success(body)
                }
            }

            response.errorBody()?.let { responseBody ->
                val errorData = GsonBuilder()
                    .setLenient()
                    .create().fromJson(responseBody.charStream(), ErrorData::class.java)
                errorData?.let {
                    return error(OpenWeatherException(it))
                }
            }

            return error(Exception(response.message()))
        } catch (e: Exception) {
            return error(e)
        }
    }

    private fun <T> error(exception: java.lang.Exception): Result<T> {
        return Result.error(exception)
    }
}
