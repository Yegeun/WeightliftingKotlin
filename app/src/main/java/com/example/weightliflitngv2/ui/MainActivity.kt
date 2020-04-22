package com.example.weightliflitngv2.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences:SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        val email = mSharedPreference.getString("email", null)
        Toast.makeText(applicationContext,email,Toast.LENGTH_SHORT).show()
//        val coach_email = sharedPref.getString("email", null)

        buttonAdd.setOnClickListener(){
            startActivity(Intent( applicationContext, AddAthlete::class.java ))
        }
    }
}
