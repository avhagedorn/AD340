package com.example.kotlinapp.api

import com.squareup.moshi.Json

data class WeatherItems(val icon: String, val main: String)
data class Forecast(val temp: Float, val icon: String)
data class Coordinates(val lat: Float, val lon: Float)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name = "main") val forecast: Forecast,
    val weather: List<WeatherItems>
)