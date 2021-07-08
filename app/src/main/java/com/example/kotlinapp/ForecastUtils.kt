package com.example.kotlinapp

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTemp(temp: Float, unit: TempChoices) : String {
    return when(unit) {
        TempChoices.Fahrenheit -> String.format("%.2f° F", temp)
        TempChoices.Celsius -> {
            val tempC = (temp - 32f) * 5f/9f
            String.format("%.2f° C", tempC)
        }
    }
}

fun tempDialog(context: Context, tempDisplaySettings: TempDisplaySettings) {
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Unit Preference")
        .setMessage("Choose a temperature unit!")
        .setPositiveButton("F") {_,_ -> tempDisplaySettings.updateSetting(TempChoices.Fahrenheit)}
        .setNeutralButton("C") {_,_ -> tempDisplaySettings.updateSetting(TempChoices.Celsius)}
        .setOnDismissListener { Toast.makeText(context, "Setting will take effect on restart.", Toast.LENGTH_SHORT).show() }
    dialogBuilder.show()
}