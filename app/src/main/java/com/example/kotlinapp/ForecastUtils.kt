package com.example.kotlinapp

fun formatTemp(temp: Float, unit: TempChoices) : String {
    return when(unit) {
        TempChoices.Fahrenheit -> String.format("%.2f° F", temp)
        TempChoices.Celsius -> {
            val tempC = (temp - 32f) * 5f/9f
            String.format("%.2f° C", tempC)
        }
    }
}