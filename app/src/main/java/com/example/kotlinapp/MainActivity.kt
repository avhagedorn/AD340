package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ssnEditText: EditText = findViewById<EditText>(R.id.etSSN)
        val ssnSubmit: Button = findViewById<Button>(R.id.btnSubmitSSN)

        ssnSubmit.setOnClickListener {
            val ssn: String = ssnEditText.text.toString()
            if (ssn.length == 5)
                Toast.makeText(this, ssn, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Invalid zipcode!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}