package com.vdc.assignment.model

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.vdc.assignment.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
data class ForecastData (
	@SerializedName("dt") val date : Long? = 0,
	@SerializedName("temp") val temp : Temp? = null,
	@SerializedName("pressure") val pressure : Int? = 0,
	@SerializedName("humidity") val humidity : Int? = 0,
	@SerializedName("weather") val weather : List<Weather>? = emptyList(),
) {

	fun getDisplayDate(): String = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
			.format(Date((date ?: 0)* 1000))

	fun toString(context: Context): String {
		return String.format(
			context.getString(R.string.talkBack_forecast),
			getDisplayDate(),
			temp?.getAvgTemperature(),
			pressure,
			humidity,
			weather?.get(0)?.description
		)
	}
}