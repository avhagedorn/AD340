package com.example.kotlinapp.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlinapp.AppNavigator
import com.example.kotlinapp.MainActivity
import com.example.kotlinapp.R

class LocationEntryFragment : Fragment() {

    lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment, standard approach
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        val zipEditText: EditText = view.findViewById(R.id.etZip)
        val zipSubmit: Button = view.findViewById(R.id.btnSubmitZip)

        zipSubmit.setOnClickListener {
            val zipcode: String = zipEditText.text.toString()
            if (zipcode.length == 5) {
                appNavigator.goToCurrentForecast(zipcode)
            }
            else
                Toast.makeText(requireContext(), "Invalid zipcode!", Toast.LENGTH_SHORT).show()
        }

        return view

    }

}