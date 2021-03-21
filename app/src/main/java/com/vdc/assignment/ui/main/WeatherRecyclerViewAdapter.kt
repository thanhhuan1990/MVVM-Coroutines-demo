package com.vdc.assignment.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vdc.assignment.R
import com.vdc.assignment.model.ForecastData

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherRecyclerViewAdapter(private val onClick: IOnItemClick? = null) : RecyclerView.Adapter<WeatherViewHolder>() {

    private val mForecast: ArrayList<ForecastData?> = ArrayList()
    fun updateData(forecast: List<ForecastData?>) {

        val diffResult = DiffUtil.calculateDiff(WeatherDiffUtils(mForecast, forecast))
        this.mForecast.clear()
        this.mForecast.addAll(forecast)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val viewHolder = WeatherViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_weather,
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                mForecast[position]?.let {
                    onClick?.onItemClick(it)
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(mForecast[position])
    }

    override fun getItemCount(): Int = mForecast.size

    inner class WeatherDiffUtils(
        private val oldForecast: List<ForecastData?>,
        private val newForecast: List<ForecastData?>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldForecast.size
        }

        override fun getNewListSize(): Int {
            return newForecast.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldForecast[oldItemPosition]?.date == newForecast[newItemPosition]?.date
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldForecast[oldItemPosition] == newForecast[newItemPosition]
        }
    }

    interface IOnItemClick {
        fun onItemClick(item: ForecastData)
    }
}

