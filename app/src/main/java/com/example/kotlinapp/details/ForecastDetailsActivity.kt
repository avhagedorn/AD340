package com.example.kotlinapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import com.example.kotlinapp.R
import com.example.kotlinapp.formatTemp

class ForecastDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)
        setTitle(R.string.forecast_details)

        val tempText = findViewById<TextView>(R.id.txtDetailedTemp)
        val descriptionText = findViewById<TextView>(R.id.txtDetailedDescription)

        val temp = intent.getFloatExtra("temperature", 0f)

        tempText.text = formatTemp(temp)
        descriptionText.text = intent.getStringExtra("forecast")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }
}