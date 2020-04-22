package com.example.weightliflitngv2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.example.weightliflitngv2.data.NODE_COACH_PROFILE
import com.example.weightliflitngv2.data.ProfileCoach
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialogue_fragment_athlete_profile.*


class ProfileAthleteDialogue: DialogFragment() {

    private val dbCoaches = FirebaseDatabase.getInstance().getReference(NODE_COACH_PROFILE)

    private var list = ArrayList<String>()
    lateinit var data: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the view model of the data that gets passed to save it into the database
        return inflater.inflate(R.layout.dialogue_fragment_athlete_profile, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

        val dbCoaches = FirebaseDatabase.getInstance().getReference(NODE_COACH_PROFILE)


        val spinner = R.id.spinner_athletes
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,list)
        spinner.adapter = adapter
        //        val messageListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                list.clear()
//                if (dataSnapshot.exists()) {
//                    val message = dataSnapshot.children
//                    message.forEach(){
//                        data = it.getValue(ProfileCoach::class.java)!!.toString()
//                        list.add(data)
//                    }
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
        }
//        dbCoaches.addValueEventListener(messageListener)


    }

        //   fun onDataChange(dataSnapshot: DataSnapshot) {
//        list.clear()
//        if (dataSnapshot.exists()) {
//            val message = dataSnapshot.children
//            message.forEach(){
//                data = it.getValue<Exercises>(Exercises::class.java)!!
//                list.add(data.name.toString())
//            }
//            textView.text = list.toString()
//        }
//    }
}
