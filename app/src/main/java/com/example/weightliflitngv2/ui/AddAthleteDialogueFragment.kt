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
import com.example.weightliflitngv2.data.NODE_ATHLETE_EMAIL
import com.example.weightliflitngv2.data.NODE_COACH_EMAIL
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialogue_fragment_add_athlete.*


class AddAthleteDialogueFragment : DialogFragment() {
    private lateinit var viewModel: AddAthleteViewModel // create a view model

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the view model of the data that gets passed to save it into the database
        viewModel = ViewModelProvider(this).get(AddAthleteViewModel::class.java)
        return inflater.inflate(R.layout.dialogue_fragment_add_athlete, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref: SharedPreferences =
            requireActivity().getPreferences(Context.MODE_PRIVATE)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.athlete_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

        button_add_athlete.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            var email = edit_text_athlete_email.text.toString().trim()
            //https://stackoverflow.com/questions/50267646/kotlin-replace-multiple-characters-all-in-string
            email = email.replace(Regex("""[@,.]"""), "_") // replaces with underscore so it can save it

            if (name.isEmpty()) { //check if they are a coach if it has a name
                input_layout_name.error = getString(R.string.error_field)
                return@setOnClickListener
            }
            val athlete = Athlete()
            athlete.name = name
            athlete.coach = NODE_COACH_EMAIL
            athlete.email = email
            viewModel.addAthlete(athlete, email)

        }
    }
}

