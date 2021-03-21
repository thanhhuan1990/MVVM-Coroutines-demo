package com.vdc.assignment.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
data class Temp (
	@SerializedName("day") var day : Double? = 0.0,
	@SerializedName("min") var min : Double? = 0.0,
	@SerializedName("max") var max : Double? = 0.0,
	@SerializedName("night") var night : Double? = 0.0,
	@SerializedName("eve") var eve : Double? = 0.0,
	@SerializedName("morn") var morn : Double? = 0.0
) {
	constructor(avgTemp: Int) : this() {
		this.day = avgTemp.toDouble() + 273.15
		this.min = avgTemp.toDouble() + 273.15
		this.max = avgTemp.toDouble() + 273.15
		this.night = avgTemp.toDouble() + 273.15
		this.eve = avgTemp.toDouble() + 273.15
		this.morn = avgTemp.toDouble() + 273.15
	}
	/**
	 * Celsius = Kelvin - 273.15
	 */
	fun getAvgTemperature() = (listOf(day, min, max, night, eve, morn).toList()
		.map { value -> value ?: 0.0 }
		.average() - 273.15).roundToInt()
}