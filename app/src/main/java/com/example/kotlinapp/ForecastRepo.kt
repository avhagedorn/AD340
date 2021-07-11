package com.example.kotlinapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinapp.api.CurrentWeather
import com.example.kotlinapp.api.WeeklyForecast
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
    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast: LiveData<WeeklyForecast> = _weeklyForecast

    fun loadWeeklyForecast(zipcode: String) {
        val service = createOpenWeatherMapService()
        val call = service.currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object: Callback<CurrentWeather> {
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    val weekly = service.weeklyWeather(
                        lat=weatherResponse.coord.lat,
                        lon=weatherResponse.coord.lon,
                        exclude="current,minutely,hourly",
                        units="imperial",
                        apiKey=BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    weekly.enqueue(object: Callback<WeeklyForecast> {
                        override fun onResponse(call: Call<WeeklyForecast>, response: Response<WeeklyForecast>) {
                            val weatherResponse = response.body()
                            if (weatherResponse != null) {
                                _weeklyForecast.value = weatherResponse
                            }
                        }
                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepo::class.java.simpleName, "Error loading weekly forecast", t)
                        }

                    })
                }
            }
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepo::class.java.simpleName, "Error loading location for forecast", t)
            }
        })
    }

    fun loadCurrentForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)

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