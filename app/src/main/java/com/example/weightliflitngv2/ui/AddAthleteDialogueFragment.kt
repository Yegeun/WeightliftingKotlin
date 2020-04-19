package com.example.weightliflitngv2.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import kotlinx.android.synthetic.main.dialogue_fragment_add_athlete.*


class AddAthleteDialogueFragment : DialogFragment() {

    private lateinit var viewModel: AddAthleteViewModel // create a view model

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // use the view model of the data that gets passed to save it into the database
        viewModel = ViewModelProviders.of(this).get(AddAthleteViewModel::class.java)
        return inflater.inflate(R.layout.dialogue_fragment_add_athlete, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer {
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
            viewModel.addAthlete(athlete)
        }

    }

}
