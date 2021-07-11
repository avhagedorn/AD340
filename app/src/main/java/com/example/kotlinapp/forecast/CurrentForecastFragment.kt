package com.example.kotlinapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.*
import com.example.kotlinapp.api.CurrentWeather
import com.example.kotlinapp.api.DailyForecast
import com.example.kotlinapp.details.ForecastDetailsFragmentArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()
    private lateinit var tempDisplaySettings: TempDisplaySettings
    private lateinit var locationRepo: LocationRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        val locationName: TextView = view.findViewById(R.id.txtLocation)
        val currentTemp: TextView = view.findViewById(R.id.txtCurrentTemp)
        tempDisplaySettings = TempDisplaySettings(requireContext())

        val currentWeatherObserver = Observer<CurrentWeather> {
            locationName.text = it.name
            Log.d("API Temperature", it.forecast.temp.toString())
            currentTemp.text = formatTemp(it.forecast.temp, tempDisplaySettings.getSetting())
        }
        forecastRepo.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val zipBtn = view.findViewById<FloatingActionButton>(R.id.btnCurrentNav)
        zipBtn.setOnClickListener {
            goToZipcodeMenu()
        }

        locationRepo = LocationRepo(requireContext())
        val savedLocationObserver = Observer<Location> {location ->
            when (location) {
                is Location.Zipcode -> forecastRepo.loadCurrentForecast(location.zipcode)
            }
        }
        locationRepo.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        return view
    }

    private fun goToZipcodeMenu() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

}