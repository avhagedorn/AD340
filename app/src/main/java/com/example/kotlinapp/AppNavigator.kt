package com.example.kotlinapp

interface AppNavigator {
    fun goToCurrentForecast(zipcode: String)
    fun goToZipcodeMenu()
    fun showForecastDetails(forecastData: ForecastDaily)
}
