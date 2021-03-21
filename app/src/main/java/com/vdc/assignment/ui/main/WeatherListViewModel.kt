package com.vdc.assignment.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdc.assignment.BuildConfig
import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.repository.net.Result.Status
import com.vdc.assignment.repository.net.repository.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherListViewModel
    @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    private val _result = MutableLiveData<List<ForecastData?>>()
    val result: LiveData<List<ForecastData?>> = _result

    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> = _error

    /**
     * Search Weather by Key, called from XML
     * @param searchKey: String from edtSearch
     * @param ctx: Context for getString
     **/
    fun searchWeather(searchKey: String) {
        _result.value = arrayOfNulls<ForecastData?>(7).toList()
        viewModelScope.launch {
            val result = repository.getWeather(searchKey, BuildConfig.AppId)
            when (result.status) {
                Status.SUCCESS -> {
                    _result.value = result.data?.forecastData ?: emptyList()
                }
                Status.ERROR -> {
                    _result.value = emptyList()
                    // Show error message
                    result.exception?.let {
                        _error.value = it
                        _error.value = null
                    }
                }
            }
        }
    }
}
