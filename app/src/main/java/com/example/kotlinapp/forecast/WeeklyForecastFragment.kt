package com.example.kotlinapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.*
import com.example.kotlinapp.api.DailyForecast
import com.example.kotlinapp.api.WeeklyForecast
import com.example.kotlinapp.details.ForecastDetailsFragmentArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeeklyForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()
    private lateinit var tempDisplaySettings: TempDisplaySettings
    private lateinit var locationRepo: LocationRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        tempDisplaySettings = TempDisplaySettings(requireContext())
        val forecastList: RecyclerView = view.findViewById<RecyclerView>(R.id.rvForecast)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val forecastDailyAdapter = ForecastDailyAdapter(tempDisplaySettings) {
            goToDetailedForecast(it)
        }
        forecastList.adapter = forecastDailyAdapter
        val emptyText = view.findViewById<TextView>(R.id.txtInitialPrompt)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val currentForecastObserver = Observer<WeeklyForecast> {
            progressBar.visibility = View.GONE
            emptyText.visibility = View.GONE
            forecastList.visibility = View.VISIBLE
            forecastDailyAdapter.submitList(it.daily)
        }
        forecastRepo.weeklyForecast.observe(viewLifecycleOwner, currentForecastObserver)

        val zipBtn = view.findViewById<FloatingActionButton>(R.id.btnWeeklyNav)
        zipBtn.setOnClickListener {
            goToZipcodeMenu()
        }

        locationRepo = LocationRepo(requireContext())
        val savedLocationObserver = Observer<Location> {location ->
            when (location) {
                is Location.Zipcode -> {
                    progressBar.visibility = View.VISIBLE
                    forecastRepo.loadWeeklyForecast(location.zipcode)
                }
            }
        }
        locationRepo.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }

    private fun goToZipcodeMenu() {
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun goToDetailedForecast(forecastData: DailyForecast) {
        val temp = forecastData.temp.max
        val description = forecastData.weather[0].description
        val icon = forecastData.weather[0].icon
        val date = forecastData.date
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp, description, icon, date)
        findNavController().navigate(action)
    }
}