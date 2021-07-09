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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.details.ForecastDetailsActivity
import com.example.kotlinapp.forecast.CurrentForecastFragment
import com.example.kotlinapp.forecast.CurrentForecastFragmentDirections
import com.example.kotlinapp.location.LocationEntryFragment
import com.example.kotlinapp.location.LocationEntryFragmentDirections

class MainActivity : AppCompatActivity(), AppNavigator {

    private lateinit var tempDisplaySettings: TempDisplaySettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettings = TempDisplaySettings(this)
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

    override fun goToCurrentForecast(zipcode: String) {
        val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentForecastFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

    override fun goToZipcodeMenu() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)
    }

}