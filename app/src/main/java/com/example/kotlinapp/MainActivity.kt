package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val forecastList: RecyclerView = findViewById<RecyclerView>(R.id.rvForecast)
        forecastList.layoutManager = LinearLayoutManager(this)
        val forecastDailyAdapter = ForecastDailyAdapter() {
            val msg = getString(R.string.forecast_clicked_format, it.temp, it.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = forecastDailyAdapter

        val weeklyForecastObserver = Observer<List<ForecastDaily>> {
            forecastDailyAdapter.submitList(it)
        }

        forecastRepo.weeklyForecast.observe(this, weeklyForecastObserver)

    }



}