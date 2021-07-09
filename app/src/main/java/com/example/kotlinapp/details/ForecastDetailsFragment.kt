package com.example.kotlinapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.kotlinapp.ForecastDaily
import com.example.kotlinapp.R
import com.example.kotlinapp.TempDisplaySettings
import com.example.kotlinapp.forecast.CurrentForecastFragment
import com.example.kotlinapp.formatTemp

class ForecastDetailsFragment : Fragment() {

    private lateinit var tempDisplaySettings: TempDisplaySettings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast_details, container, false)

        tempDisplaySettings = TempDisplaySettings(view.context) // ?

        val args: ForecastDetailsFragmentArgs by navArgs()

        val tempText = view.findViewById<TextView>(R.id.txtDetailedTemp)
        val descriptionText = view.findViewById<TextView>(R.id.txtDetailedDescription)

        tempText.text = formatTemp(args.temperature, tempDisplaySettings.getSetting())
        descriptionText.text = args.description

        return view
    }

}