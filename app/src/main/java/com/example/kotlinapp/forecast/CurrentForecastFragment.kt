package com.example.kotlinapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.*
import com.example.kotlinapp.details.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()
    private lateinit var tempDisplaySettings: TempDisplaySettings
    lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val zipcode = requireArguments().getString(ZIPCODE) ?: ""
        tempDisplaySettings = TempDisplaySettings(requireContext())
        val forecastList: RecyclerView = view.findViewById<RecyclerView>(R.id.rvForecast)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val forecastDailyAdapter = ForecastDailyAdapter(tempDisplaySettings) {
            // showForecastDetails(it)
            appNavigator.showForecastDetails(it)
        }
        forecastList.adapter = forecastDailyAdapter

        val weeklyForecastObserver = Observer<List<ForecastDaily>> {
            forecastDailyAdapter.submitList(it)
        }

        val zipBtn = view.findViewById<FloatingActionButton>(R.id.btnChangeLocation)
        zipBtn.setOnClickListener {
            appNavigator.goToZipcodeMenu()
        }

        forecastRepo.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)
        forecastRepo.loadForecast(zipcode)

        return view
    }

    // TODO: REFACTOR ME
//    private fun showForecastDetails(forecastData: ForecastDaily) {
//        val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
//        forecastDetailsIntent.putExtra("temperature", forecastData.temp)
//        forecastDetailsIntent.putExtra("forecast", forecastData.description)
//        startActivity(forecastDetailsIntent)
//    }

    companion object {
        const val ZIPCODE = "zipcode"

        fun newInstance(zipcode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()
            val args = Bundle()
            args.putString(ZIPCODE, zipcode)
            fragment.arguments = args
            return fragment
        }
    }
}