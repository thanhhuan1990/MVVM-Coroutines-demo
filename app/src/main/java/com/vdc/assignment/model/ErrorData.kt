package com.vdc.assignment.model

import com.google.gson.annotations.SerializedName
import java.lang.Exception

/**
 * Created by Huan.Huynh on 20/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
data class ErrorData(
    @SerializedName(value = "cod") val cod: String? = "",
    @SerializedName(value = "message") val message: String? = "",
)

class OpenWeatherException(val errorData: ErrorData) : Exception()