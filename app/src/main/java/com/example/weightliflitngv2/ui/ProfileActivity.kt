package com.example.weightliflitngv2.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        button_profile_coach.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val newFragment = ProfileCoachDialogue()
            newFragment.show(ft, "")
        }
    }
}






