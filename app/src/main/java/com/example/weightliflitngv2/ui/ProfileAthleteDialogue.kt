package com.example.weightliflitngv2.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.dialogue_fragment_athlete_profile.*


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
            if(email.isEmpty()){
                input_layer.error="Errornoinput"
                return@setOnClickListener
            }
            val editor = prefs.edit()
            editor.putString("athlete_email", email)
            editor.apply()


            dismiss()
        }

    }

}

