package com.example.kotlinapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.*
import com.example.kotlinapp.details.ForecastDetailsFragmentArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeeklyForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()
    private lateinit var tempDisplaySettings: TempDisplaySettings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val zipcode = "11111"
        tempDisplaySettings = TempDisplaySettings(requireContext())
        val forecastList: RecyclerView = view.findViewById<RecyclerView>(R.id.rvForecast)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val forecastDailyAdapter = ForecastDailyAdapter(tempDisplaySettings) {
            goToDetailedForecast(it)
        }
        forecastList.adapter = forecastDailyAdapter

        val weeklyForecastObserver = Observer<List<ForecastDaily>> {
            forecastDailyAdapter.submitList(it)
        }

        val zipBtn = view.findViewById<FloatingActionButton>(R.id.btnWeeklyNav)
        zipBtn.setOnClickListener {
            goToZipcodeMenu()
        }

        forecastRepo.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)
        forecastRepo.loadForecast(zipcode)

        return view
    }

    private fun goToZipcodeMenu() {
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun goToDetailedForecast(forecastData: ForecastDaily) {
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecastData.temp, forecastData.description)
        findNavController().navigate(action)
    }
}