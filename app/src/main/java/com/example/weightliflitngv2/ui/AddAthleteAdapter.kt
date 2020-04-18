package com.example.weightliflitngv2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import kotlinx.android.synthetic.main.recycler_view_athlete.view.*

class AddAthleteAdapter : RecyclerView.Adapter<AddAthleteAdapter.AddAthleteViewModel>(){

    private var athletes = mutableListOf<Athlete>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        AddAthleteViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_athlete, parent, false)
    )

    override fun getItemCount() = athletes.size

    override fun onBindViewHolder(holder: AddAthleteViewModel, position: Int) {// where the name is in the recycler view
        holder.view.text_view_name.text= athletes[position].name
    }

    fun setAthletes(athletes: List<Athlete>){
        this.athletes = athletes as MutableList<Athlete> // chanign into a unmutable list
        notifyDataSetChanged()
    }

    fun addAthlete(athlete: Athlete) {
        if (!athletes.contains(athlete)) {
            athletes.add(athlete)
            notifyDataSetChanged()
        }
    }
    class AddAthleteViewModel(val view: View): RecyclerView.ViewHolder(view)
}