package com.example.kotlinapp

import android.content.Context

enum class TempChoices {
    Fahrenheit, Celsius
}

class TempDisplaySettings(context: Context) {
    // Opens shared preferences
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSetting(setting: TempChoices) {
        preferences.edit().putString("temp_display", setting.name).commit()
    }

    fun getSetting() : TempChoices {
        val settingValue = preferences.getString("temp_display", TempChoices.Fahrenheit.name) ?: TempChoices.Fahrenheit.name
        return TempChoices.valueOf(settingValue)
    }
}