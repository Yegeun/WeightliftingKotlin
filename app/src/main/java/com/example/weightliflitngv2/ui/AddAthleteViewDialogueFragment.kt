package com.example.weightliflitngv2.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.example.weightliflitngv2.data.NODE_ATHLETE_EMAIL
import com.example.weightliflitngv2.data.NODE_DATE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_date
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_exercise1
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_exercise2
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_exercise3
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_exercise4
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_name
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_rep1
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_rep2
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_rep3
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_rep4
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_set1
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_set2
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_set3
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_set4
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_weight1
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_weight2
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_weight3
import kotlinx.android.synthetic.main.dialogue_view_exercise.textView_weight4


class AddAthleteViewDialogueFragment(private val athlete: Athlete) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the view model of the data that gets passed to save it into the database
        return inflater.inflate(R.layout.dialogue_view_exercise, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        textView_name.text= mSharedPreference.getString("athlete_email", null).toString()
        textView_date.text= NODE_DATE
        athlete.email= NODE_ATHLETE_EMAIL

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


