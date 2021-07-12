package com.example.kotlinapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
        val icon: ImageView = view.findViewById(R.id.imgDetailedIcon)
        val description: TextView = view.findViewById(R.id.txtCurrentDescription)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar2)
        val emptyText = view.findViewById<TextView>(R.id.txtInitialPrompt)
        tempDisplaySettings = TempDisplaySettings(requireContext())

        val currentWeatherObserver = Observer<CurrentWeather> {
            progressBar.visibility = View.GONE
            emptyText.visibility = View.GONE
            description.visibility = View.VISIBLE
            icon.visibility = View.VISIBLE
            currentTemp.visibility = View.VISIBLE
            locationName.visibility = View.VISIBLE
            locationName.text = it.name
            currentTemp.text = formatTemp(it.forecast.temp, tempDisplaySettings.getSetting())
            description.text = it.weather[0].main

            val iconId = it.weather[0].icon
            icon.load("https://openweathermap.org/img/wn/$iconId@2x.png")
        }
        forecastRepo.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val zipBtn = view.findViewById<FloatingActionButton>(R.id.btnCurrentNav)
        zipBtn.setOnClickListener {
            goToZipcodeMenu()
        }

        locationRepo = LocationRepo(requireContext())
        val savedLocationObserver = Observer<Location> {location ->
            when (location) {
                is Location.Zipcode -> {
                    progressBar.visibility = View.VISIBLE
                    forecastRepo.loadCurrentForecast(location.zipcode)
                }
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