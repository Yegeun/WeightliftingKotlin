package com.example.weightliflitngv2.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.example.weightliflitngv2.data.NODE_ATHLETE_EMAIL
import com.example.weightliflitngv2.data.NODE_DATE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_view_exercise.*

class ViewExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_exercise)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        textView_name.text= mSharedPreference.getString("athlete_email", null).toString()
        textView_date.text= NODE_DATE
        fetchAthleteExercises()
    }

    private fun fetchAthleteExercises(){
        FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
            .child(NODE_ATHLETE_EMAIL)
            .child(NODE_DATE)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        val post = dataSnapshot.value as Map<*, *>
                        textView_exercise1.text = post["exercise1"].toString()
                        textView_weight1.text = post["weight1"].toString()
                        textView_set1.text = post["set1"].toString()
                        textView_rep1.text = post["rep1"].toString()
                        textView_exercise2.text = post["exercise2"].toString()
                        textView_weight2.text = post["weight2"].toString()
                        textView_set2.text = post["set2"].toString()
                        textView_rep2.text = post["rep2"].toString()
                        textView_exercise3.text = post["exercise3"].toString()
                        textView_weight3.text = post["weight3"].toString()
                        textView_set3.text = post["set3"].toString()
                        textView_rep3.text = post["rep3"].toString()
                        textView_exercise4.text = post["exercise4"].toString()
                        textView_weight4.text = post["weight4"].toString()
                        textView_set4.text = post["set4"].toString()
                        textView_rep4.text = post["rep4"].toString()
                    }
                }
            })

    }
}

