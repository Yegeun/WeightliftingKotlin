package com.example.weightliflitngv2.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_ATHLETE_EMAIL
import com.example.weightliflitngv2.data.NODE_COACH_EMAIL

class AddAthlete : AppCompatActivity() {
    lateinit var email:String
    private lateinit var athleteEmail:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_athlete)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        email = mSharedPreference.getString("email", null).toString()
        athleteEmail = mSharedPreference.getString("athlete_email", null).toString()

        athleteEmail = athleteEmail.replace(Regex("""[@,.]"""), "_") // replaces with underscore so it can save it
        NODE_COACH_EMAIL =email
        NODE_ATHLETE_EMAIL =athleteEmail
    }
}
