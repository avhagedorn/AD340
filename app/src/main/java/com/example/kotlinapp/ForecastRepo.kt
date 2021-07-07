package com.example.kotlinapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.round
import kotlin.random.Random

class ForecastRepo {

    private val _weeklyForecast = MutableLiveData<List<ForecastDaily>>()
    val weeklyForecast: LiveData<List<ForecastDaily>> = _weeklyForecast

    fun loadForecast(zipcode: String) {
        val randomValues = List(7) { Random.nextDouble().rem(100) * 100 }
        val forecastItems = randomValues.map {
            ForecastDaily(it, "Partly Cloudy")
        }
        _weeklyForecast.value = forecastItems
    }
}