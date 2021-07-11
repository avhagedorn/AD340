package com.example.kotlinapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.kotlinapp.R
import com.example.kotlinapp.TempDisplaySettings
import com.example.kotlinapp.databinding.FragmentForecastDetailsBinding
import com.example.kotlinapp.forecast.CurrentForecastFragment
import com.example.kotlinapp.formatTemp

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettings: TempDisplaySettings

    private var _binding: FragmentForecastDetailsBinding? = null
    // Only valid between onCreateView and onDestroyView
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        tempDisplaySettings = TempDisplaySettings(requireContext())
        binding.txtDetailedTemp.text = formatTemp(args.temperature, tempDisplaySettings.getSetting())
        binding.txtDetailedDescription.text = args.description
        binding.imgDetailed.load("https://openweathermap.org/img/wn/${args.icon}@2x.png")

        return binding.root
    }

    // Cleans up memory
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}