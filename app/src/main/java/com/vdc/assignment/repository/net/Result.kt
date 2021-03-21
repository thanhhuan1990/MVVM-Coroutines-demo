package com.vdc.assignment.repository.net

import java.lang.Exception

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
data class Result<out T>(val status: Status, val data: T?, val exception: Exception?) {

    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Exception?, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, exception)
        }
    }
}