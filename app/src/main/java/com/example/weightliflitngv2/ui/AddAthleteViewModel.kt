package com.example.weightliflitngv2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliflitngv2.data.Athlete
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.google.firebase.database.*

//simply coding
class AddAthleteViewModel : ViewModel() {
    private val dbAthletes = FirebaseDatabase.getInstance().getReference(NODE_ATHLETES) // reference for each node Athletes talk

    private val _athletes = MutableLiveData<List<Athlete>>() // this relates to the fetch athletes
    val athletes: LiveData<List<Athlete>>
        get() =_athletes

    private val _athlete = MutableLiveData<Athlete>() // this relates to the fetch athletes
    val athlete: LiveData<Athlete>
        get() =_athlete

    private val _result = MutableLiveData<Exception?>()
    val result:LiveData<Exception?>
        get() = _result

    fun addAthlete(athlete: Athlete){
        athlete.id = dbAthletes.push().key
        dbAthletes.child(athlete.id!!).setValue(athlete) // db athletes.child(is the save of the id)
        // * set value is the what it saves too
            .addOnCompleteListener { // when the complete listener is completed
                if(it.isSuccessful){
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }
    fun updateAthlete(athlete: Athlete){
        dbAthletes.child(athlete.id!!).setValue(athlete)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }

    fun deleteAthlete(athlete: Athlete){
        dbAthletes.child(athlete.id!!).setValue(null)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }


        // to use this call the function using view model get rid of updates and and dbathletes data change
        // and
//    dbAthletes.addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onCancelled(error: DatabaseError) {}
//
//        override fun onDataChange(snapshot: DataSnapshot) {
        // if its one use
        // if(snapshotl.exists()) {_author.value = snapshot.getV(Author::class.java}
//            if (snapshot.exists()) {
//                val authors = mutableListOf<Author>()
//                for (authorSnapshot in snapshot.children) {
//                    val author = authorSnapshot.getValue(Author::class.java)
//                    author?.id = authorSnapshot.key
//                    author?.let { authors.add(it) }
//                }
//                _authors.value = authors
//            }// ust hthis if using complete list of things
//        }
//    })



    private val childEventListener = object : ChildEventListener{
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val athlete = snapshot.getValue(Athlete::class.java)
            athlete?.id = snapshot.key
            _athlete.value = athlete
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val athlete= snapshot.getValue(Athlete::class.java)
            athlete?.id = snapshot.key
            _athlete.value = athlete
        }

        override fun onChildRemoved(snapshot: DataSnapshot) { // deleted confirmation
            val athlete = snapshot.getValue(Athlete::class.java)
            athlete?.id = snapshot.key
            athlete?.isDeleted = true
            _athlete.value = athlete
        }
    }
    fun getRealtimeUpdate(){
        dbAthletes.addChildEventListener(childEventListener)
    }

    fun fetchAthletes(){
        dbAthletes.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onCancelled(error: DatabaseError){}
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()){
                    val athletes= mutableListOf<Athlete>()
                    for (athleteSnapshot in snapshot.children) {
                        val athlete = athleteSnapshot.getValue(Athlete::class.java)
                        athlete?.id = athleteSnapshot.key
                        athlete?.let { athletes.add(it) }
                    }
                    _athletes.value = athletes
                }
            }
        })
    }

    override fun onCleared(){
        super.onCleared()
        dbAthletes.removeEventListener(childEventListener)
    }// this kills to stop listening

}


