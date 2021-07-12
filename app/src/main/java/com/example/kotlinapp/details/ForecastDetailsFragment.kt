package com.example.kotlinapp.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.kotlinapp.TempDisplaySettings
import com.example.kotlinapp.databinding.FragmentForecastDetailsBinding
import com.example.kotlinapp.formatTemp

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    // Enables view model scoping
    private val viewModel: ForecastDetailsViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

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
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState> { viewState ->
            binding.txtDetailedTemp.text = formatTemp(viewState.temp, tempDisplaySettings.getSetting())
            binding.txtDetailedDescription.text = viewState.details
            binding.txtDetailedDate.text = viewState.date
            binding.imgDetailed.load(viewState.icon)
        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    // Cleans up memory
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}