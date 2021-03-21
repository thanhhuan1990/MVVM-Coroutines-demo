package com.vdc.assignment.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
data class WeatherResponse(
    @SerializedName(value = "list") val forecastData: List<ForecastData>? = emptyList(),
)