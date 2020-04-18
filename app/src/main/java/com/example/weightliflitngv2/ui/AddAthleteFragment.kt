package com.example.weightliflitngv2.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.add_athlete_fragment.*

class AddAthleteFragment : Fragment(){

    private lateinit var viewModel: AddAthleteViewModel
    private val adapter = AddAthleteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(AddAthleteViewModel::class.java)
        return inflater.inflate(R.layout.add_athlete_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_athletes.adapter = adapter

        // TODO: Use the ViewModel
        viewModel.fetchAthletes()
        viewModel.getRealtimeUpdate()

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

}
