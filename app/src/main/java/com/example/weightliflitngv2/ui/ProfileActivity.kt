package com.example.weightliflitngv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.children
import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    private lateinit var viewCoach:View
    private lateinit var viewAthlete:View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewCoach = layoutInflater.inflate(R.layout.layout_fragment_coach_profile, null)
        viewAthlete = layoutInflater.inflate(R.layout.layout_fragment_athlete_profile, null)

        radioGroup = findViewById(R.id.radio_profile)
        val id = radioGroup.checkedRadioButtonId




        }
    fun onRadioButtonClicked(view: View) {
        //layout inflater

        val layout = findViewById<LinearLayout>(R.id.linearlayout0)
        Toast.makeText(this,layout.childCount.toString(),Toast.LENGTH_SHORT).show()
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.id) {
                R.id.radio_coach ->
                    if (checked) {
                        when (layout.childCount) {
                            4 -> {
                                layout.addView(viewCoach)
                            }
                            5 -> {
                                layout.addView(viewCoach)
                                layout.removeView(viewAthlete)
                            }
                        }
                    }
                R.id.radio_athlete ->
                    if (checked) {
                        when (layout.childCount) {
                            4 -> {
                                layout.addView(viewAthlete)
                            }
                            5 -> {
                                layout.removeView(viewCoach)
                                layout.addView(viewAthlete)
                            }
                        }

                    }
                }
            }
        }
}



