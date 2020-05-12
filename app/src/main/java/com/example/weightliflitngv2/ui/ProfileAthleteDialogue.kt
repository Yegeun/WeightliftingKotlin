package com.example.weightliflitngv2.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import kotlinx.android.synthetic.main.dialogue_fragment_athlete_profile.*
import kotlin.system.exitProcess


class ProfileAthleteDialogue: DialogFragment() {

    
    lateinit var data: String
    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.dialogue_fragment_athlete_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val athleteEmail = prefs.getString("athlete_email", null)
        if (athleteEmail != null) {
            editText_athleteEmail.setText(athleteEmail)
        }
        button_saveProfile.setOnClickListener {
            val email=editText_athleteEmail.text.toString().trim()
            //save to prefrences
            if(email.isEmpty()){
                input_layer.error="Errornoinput"
                return@setOnClickListener
            }
            val editor = prefs.edit()
            editor.putString("athlete_email", email)
            editor.apply()
            Toast.makeText(context,"Successfully Updated", Toast.LENGTH_SHORT).show()
            Toast.makeText(context,"Please Reload The App", Toast.LENGTH_SHORT).show()
            dismiss()
        }

    }

}

