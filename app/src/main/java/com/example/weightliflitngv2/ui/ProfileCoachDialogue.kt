package com.example.weightliflitngv2.ui

import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_COACH_PROFILE
import com.example.weightliflitngv2.data.ProfileCoach
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialgoue_fragment_coach_profile.*
import kotlin.system.exitProcess


class ProfileCoachDialogue : DialogFragment() {

    private lateinit var viewCoach:View
    private val dbCoaches = FirebaseDatabase.getInstance().getReference(NODE_COACH_PROFILE)
    private val _result = MutableLiveData<Exception?>()


    val result: LiveData<Exception?>
        get() = _result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the view model of the data that gets passed to save it into the database
        return inflater.inflate(R.layout.dialgoue_fragment_coach_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val coachEmail = prefs.getString("email", null)
        if(edit_text_coach_email!=null){
            edit_text_coach_email.setText(coachEmail)
        }

        button_save_profile.setOnClickListener{
            val email=edit_text_coach_email.text.toString().trim()
            if(email.isEmpty()){
                input_email.error="Errornoinput"
                return@setOnClickListener
            }
            val coachProfile= ProfileCoach()
            coachProfile.email=email
            addCoachProfile(coachProfile)
            //save
            val editor = prefs.edit()
            editor.putString("email", coachProfile.email)

            //check
            if(checkbox_coach.isChecked){
                editor.putString("coach","yes")
            }else{
                editor.putString("coach","no")
            }
            editor.apply()
            Toast.makeText(context,"Successfully Updated",Toast.LENGTH_SHORT).show()
            Toast.makeText(context,"Please Reload The App",Toast.LENGTH_SHORT).show()
            dismiss()


        } // end fo the button commits
    }
    fun addCoachProfile(profile: ProfileCoach){
        profile.id = dbCoaches.push().key
        dbCoaches.child(profile.id!!).setValue(profile)
            .addOnCompleteListener { // when the complete listener is completed
                if(it.isSuccessful){
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }

    }
}