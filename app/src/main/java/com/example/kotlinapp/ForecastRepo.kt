package com.example.kotlinapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.round
import kotlin.random.Random

class ForecastRepo {

    // Repositories describe the amount and type of data which gets created, loaded, and returned.

    private val _weeklyForecast = MutableLiveData<List<ForecastDaily>>()
    val weeklyForecast: LiveData<List<ForecastDaily>> = _weeklyForecast

    fun loadForecast(zipcode: String) {
        val randomValues = List(7) { Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map {
            ForecastDaily(it, getTempDescription(it))
        }
        _weeklyForecast.value = forecastItems
    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp) {
            in 0f..32f -> "It's freezing!"
            in 33f..65f -> "Minnesota weather."
            in 66f..80f -> "Good weather."
            else -> "It's too hot!"
        }
    }
}