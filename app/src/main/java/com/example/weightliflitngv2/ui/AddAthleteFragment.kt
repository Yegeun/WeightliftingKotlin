

package com.example.weightliflitngv2.ui

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import kotlinx.android.synthetic.main.add_athlete_fragment.*

class AddAthleteFragment : Fragment(), RecyclerViewListener {

    private lateinit var viewModel: AddAthleteViewModel
    private val adapter = AddAthleteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AddAthleteViewModel::class.java)
        return inflater.inflate(R.layout.add_athlete_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_athletes.adapter = adapter

        // TODO: Use the ViewModel
//        viewModel.fetchAthletes()
        viewModel.fetchfilteredAthlete() //use this lines to fetch specific athletes
//        viewModel.getRealtimeUpdate()

        viewModel.athletes.observe(viewLifecycleOwner, Observer {
            adapter.setAthletes(it) //
        })
            //singular athlete
        viewModel.athlete.observe(viewLifecycleOwner, Observer {
            adapter.addAthlete(it) //
        })

        button_add.setOnClickListener {
            AddAthleteDialogueFragment()
                .show(childFragmentManager, "")
        }
    }


    override fun itemClicked(view: View, athlete:Athlete){ // this is where you get the listern
        when(view.id){
            R.id.button_edit -> {
                EditAthleteDialogueFragment(athlete).show(childFragmentManager,"")
            }
            R.id.button_delete ->{
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle("Delete")
                    it.setPositiveButton("yes"){dialog, which ->
                        viewModel.deleteAthlete(athlete)
                    }
                }.create().show()
            }
            R.id.button_view_exercise -> {
                AddAthleteViewDialogueFragment(athlete).show(childFragmentManager,"")
                Toast.makeText(context, "You are now viewing todays data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

