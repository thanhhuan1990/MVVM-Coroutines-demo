package com.vdc.assignment.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.vdc.assignment.R
import com.vdc.assignment.databinding.FragmentWeatherListBinding
import com.vdc.assignment.model.ForecastData
import com.vdc.assignment.model.OpenWeatherException
import com.vdc.assignment.utils.ViewModelFactory
import com.vdc.assignment.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    /**------------------------------------------------------------------------------------------**/
    /** region declare Private Variables **/

    private var binding: FragmentWeatherListBinding by autoCleared()
    private lateinit var viewModel: WeatherListViewModel
    private lateinit var adapter: WeatherRecyclerViewAdapter

    @Inject
    lateinit var factory: ViewModelFactory<WeatherListViewModel>

    /** endregion declare Private Variables **/
    /**------------------------------------------------------------------------------------------**/


    /**------------------------------------------------------------------------------------------**/
    /** region override Fragment's callback **/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(WeatherListViewModel::class.java)
        binding.viewModel = viewModel
        setupViews()
        setupObservers()
    }

    /** endregion override Fragment's callback **/
    /**------------------------------------------------------------------------------------------**/

    /**------------------------------------------------------------------------------------------**/
    /** region Private functions **/

    private fun setupViews() {
        binding.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.btnSearch.isEnabled = s.toString().length >= 3
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        adapter = WeatherRecyclerViewAdapter(object : WeatherRecyclerViewAdapter.IOnItemClick {
            override fun onItemClick(item: ForecastData) {
                context?.let {
                    binding.recyclerView.announceForAccessibility(item.toString(it))
                }
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun setupObservers() {
        viewModel.result.observe(viewLifecycleOwner, {
            adapter.updateData(it)
        })
        viewModel.error.observe(viewLifecycleOwner, {
            it?.apply {
                when (this) {
                    is OpenWeatherException -> {
                        Toast.makeText(context, this.errorData.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    /** endregion Private functions **/
    /**------------------------------------------------------------------------------------------**/
}