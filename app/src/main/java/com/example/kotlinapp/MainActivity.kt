package com.example.kotlinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.details.ForecastDetailsActivity

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
            showForecastDetails(it)
        }
        forecastList.adapter = forecastDailyAdapter

        val weeklyForecastObserver = Observer<List<ForecastDaily>> {
            forecastDailyAdapter.submitList(it)
        }

        forecastRepo.weeklyForecast.observe(this, weeklyForecastObserver)
    }

    private fun showForecastDetails(forecastData: ForecastDaily) {
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("temperature", forecastData.temp)
        forecastDetailsIntent.putExtra("forecast", forecastData.description)
        startActivity(forecastDetailsIntent)
    }
}