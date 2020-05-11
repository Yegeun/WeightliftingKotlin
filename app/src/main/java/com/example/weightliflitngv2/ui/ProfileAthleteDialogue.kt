package com.example.weightliflitngv2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_COACH_PROFILE
import com.google.firebase.database.FirebaseDatabase


class ProfileAthleteDialogue: DialogFragment() {

    private val dbCoaches = FirebaseDatabase.getInstance().getReference(NODE_COACH_PROFILE)

    private var list = ArrayList<String>()
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

        val dbCoaches = FirebaseDatabase.getInstance().getReference(NODE_COACH_PROFILE)

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
//}
