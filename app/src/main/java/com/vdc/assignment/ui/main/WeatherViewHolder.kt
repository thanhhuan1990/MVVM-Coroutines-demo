package com.vdc.assignment.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.vdc.assignment.databinding.ItemWeatherBinding
import com.vdc.assignment.model.ForecastData

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherViewHolder(
    private val binding: ItemWeatherBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecastData: ForecastData?) {
        with(binding) {
            binding.forecastData = forecastData
            executePendingBindings()
        }
    }
}