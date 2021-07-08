package com.example.kotlinapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlinapp.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettings: TempDisplaySettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)
        setTitle(R.string.forecast_details)
        tempDisplaySettings = TempDisplaySettings(this)

        val tempText = findViewById<TextView>(R.id.txtDetailedTemp)
        val descriptionText = findViewById<TextView>(R.id.txtDetailedDescription)

        val temp = intent.getFloatExtra("temperature", 0f)

        tempText.text = formatTemp(temp, tempDisplaySettings.getSetting())
        descriptionText.text = intent.getStringExtra("forecast")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.itSettings -> {
                tempDialog(this, tempDisplaySettings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}