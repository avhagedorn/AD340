package com.example.kotlinapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class ForecastDetailsViewModelFactory(private val args: ForecastDetailsFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java))
            return ForecastDetailsViewModel(args) as T
        throw IllegalArgumentException("Unknown view model class!")
    }
}

class ForecastDetailsViewModel(args: ForecastDetailsFragmentArgs) : ViewModel() {

    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

    init {
        _viewState.value = ForecastDetailsViewState(
            details = args.description,
            temp = args.temperature,
            icon = "https://openweathermap.org/img/wn/${args.icon}@2x.png",
            date = DATE_FORMAT.format(Date(args.date * 1000))
        )
    }
}