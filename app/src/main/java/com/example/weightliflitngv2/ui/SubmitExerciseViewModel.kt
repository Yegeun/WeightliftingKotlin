package com.example.weightliflitngv2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliflitngv2.data.*
import com.google.firebase.database.FirebaseDatabase

class SubmitExerciseViewModel : ViewModel(){
    //https://simplified-coding-downloads.firebaseapp.com/download.html?id=-M-kDLRgO-6Bp9CRZSh6
    private val dbExercises = FirebaseDatabase.getInstance().getReference( NODE_ATHLETES)
        .child(NODE_ATHLETE_EMAIL)// reference for each node Athletes talk

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addExercise(exercises: Exercises){
        exercises.id = NODE_DATE
        dbExercises.child(exercises.id!!).setValue(exercises) // db athletes.child(is the save of the id)
            // * set value is the what it saves too
            .addOnCompleteListener { // when the complete listener is completed
                if(it.isSuccessful){
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }
}