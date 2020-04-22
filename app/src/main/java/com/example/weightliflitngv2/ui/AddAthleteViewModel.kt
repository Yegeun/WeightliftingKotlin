package com.example.weightliflitngv2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliflitngv2.data.Athlete
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.google.firebase.database.*


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

    fun fetchFilteredAuthors(index: Int, coach_email:String) {
        val dbAthlete =
            when (index) {
                1 ->
                    //#1 SELECT * FROM Authors
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)

                2 ->
                    //#2 SELECT * FROM Authors WHERE id = ?
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
                        .child("-M-3fFw3GbovXWguSjp8")

                3 ->
                    //#3 SELECT * FROM Authors WHERE city = ?
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
                        .orderByChild("email")
                        .equalTo("Coach")
                4 ->
                    //#4 SELECT * FROM Authors LIMIT 2
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
                        .limitToFirst(2)

                5 ->
                    //#5 SELECT * FROM Authors WHERE votes < 500
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
                        .orderByChild("votes")
                        .endAt(500.toDouble())

                6 ->
                    //#6 SELECT * FROM Artists WHERE name LIKE "A%"
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
                        .orderByChild("name")
                        .startAt("A")
                        .endAt("A\uf8ff")

                7 ->
                    //#7 SELECT * FROM Artists Where votes < 500 AND city = Bangalore
                    // not possible you have to combine database
                    FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
                else -> FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
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
    }


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

