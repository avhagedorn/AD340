package com.example.kotlinapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinapp.api.CurrentWeather
import com.example.kotlinapp.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round
import kotlin.random.Random

class ForecastRepo {

    // Repositories describe the amount and type of data which gets created, loaded, and returned.
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather
    private val _weeklyForecast = MutableLiveData<List<ForecastDaily>>()
    val weeklyForecast: LiveData<List<ForecastDaily>> = _weeklyForecast

    fun loadWeeklyForecast(zipcode: String) {
        val randomValues = List(7) { Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map {
            ForecastDaily(it, getTempDescription(it))
        }
        _weeklyForecast.value = forecastItems
    }

    fun loadCurrentForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", "a0de1401871ac3bc1fd6412833bc600e")

        call.enqueue(object: Callback<CurrentWeather> {
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    _currentWeather.value = weatherResponse
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepo::class.java.simpleName, "Error loading weather", t)
            }

        })
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