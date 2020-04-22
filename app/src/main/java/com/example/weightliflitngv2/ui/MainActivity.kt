package com.example.weightliflitngv2.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.example.weightliflitngv2.data.NODE_COACH_EMAIL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences:SharedPreferences
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        email = mSharedPreference.getString("email", null).toString()
        Toast.makeText(applicationContext,email,Toast.LENGTH_SHORT).show()
        NODE_COACH_EMAIL=email


        buttonAdd.setOnClickListener(){
            startActivity(Intent( applicationContext, AddAthlete::class.java ))
        }
    }
}
