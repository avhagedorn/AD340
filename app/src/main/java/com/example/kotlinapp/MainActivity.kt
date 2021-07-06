package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val forecastRepo = ForecastRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipEditText: EditText = findViewById<EditText>(R.id.etZip)
        val zipSubmit: Button = findViewById<Button>(R.id.btnSubmitZip)

        zipSubmit.setOnClickListener {
            val zipcode: String = zipEditText.text.toString()
            if (zipcode.length == 5) {
                forecastRepo.loadForecast(zipcode)
                Toast.makeText(this, zipcode, Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "Invalid zipcode!", Toast.LENGTH_SHORT).show()
        }

        val weeklyForecastObserver = Observer<List<ForecastDaily>> {
            // TODO: Update list adapter
        }

        forecastRepo.weeklyForecast.observe(this, weeklyForecastObserver)

    }



}