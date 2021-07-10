package com.example.kotlinapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class Location {
    data class Zipcode(val zipcode: String) : Location()
}

private const val ZIPCODE = "zipcode"

class LocationRepo(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation: LiveData<Location> = _savedLocation

    init {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key != ZIPCODE) return@registerOnSharedPreferenceChangeListener
            broadcastSavedChange()
        }
        broadcastSavedChange()
    }

    fun saveLocation(location: Location) {
        when (location) {
            is Location.Zipcode -> preferences.edit().putString(ZIPCODE, location.zipcode).apply()
        }
    }

    private fun broadcastSavedChange() {
        val zipcode = preferences.getString(ZIPCODE, "")
        if (zipcode != null && zipcode.isNotEmpty())
            _savedLocation.value = Location.Zipcode(zipcode)
    }
}