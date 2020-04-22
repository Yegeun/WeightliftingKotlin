package com.example.weightliflitngv2.ui


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialogue_fragment_add_athlete.*


class AddAthleteDialogueFragment : DialogFragment() {
    private val dbAthletes = FirebaseDatabase.getInstance().getReference(NODE_ATHLETES) // reference for each node Athletes talk

    private val _athlete = MutableLiveData<Athlete>() // this relates to the fetch athletes
    val athlete: LiveData<Athlete>
        get() =_athlete

    private val _result = MutableLiveData<Exception?>()
    private val result: LiveData<Exception?>
        get() = _result

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the view model of the data that gets passed to save it into the database

        return inflater.inflate(R.layout.dialogue_fragment_add_athlete, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       result.observe(viewLifecycleOwner, Observer {
            val message = if(it == null){
                getString(R.string.athlete_added)
            }else{
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

        button_add_athlete.setOnClickListener{
            val name = edit_text_name.text.toString().trim()

            if(name.isEmpty()){ //check if they are a coach if it has a name
                input_layout_name.error = getString(R.string.error_field)
                return@setOnClickListener
            }
            val athlete = Athlete()

            athlete.name = name
            addAthlete(athlete)
        }

    }


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


}
