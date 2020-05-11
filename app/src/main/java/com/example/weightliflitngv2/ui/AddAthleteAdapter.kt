package com.example.weightliflitngv2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Athlete
import kotlinx.android.synthetic.main.recycler_view_athlete.view.*

class AddAthleteAdapter : RecyclerView.Adapter<AddAthleteAdapter.AddAthleteViewModel>(){
    //simpifly coding

    private var athletes = mutableListOf<Athlete>()
    var listener: RecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        AddAthleteViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_athlete, parent, false)
    )

    override fun getItemCount() = athletes.size

    override fun onBindViewHolder(holder: AddAthleteViewModel, position: Int) {// where the name is in the recycler view
        // this can take the name of of the ahtlestes also have each athlete button click listener
        holder.view.text_view_name.text= athletes[position].name
        //callback
        holder.view.button_edit.setOnClickListener {
            listener?.itemClicked(it, athletes[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.itemClicked(it, athletes[position])
        }
    }

    fun setAthletes(athletes: List<Athlete>){
        this.athletes = athletes as MutableList<Athlete> // chanign into a unmutable list
        notifyDataSetChanged()
    }

    fun addAthlete(athlete: Athlete) { //this changes the list with the dialogue fragment
        if (!athletes.contains(athlete)) {
            athletes.add(athlete)
        }else{// if alredy in the list you need this to update the list
            val index  = athletes.indexOf(athlete)
            athletes[index]= athlete
            if(athlete.isDeleted){
                athletes.removeAt(index)
            }else{
                athletes[index] = athlete
            }
        }
        notifyDataSetChanged() // you need this when the data set is changed
    }
    class AddAthleteViewModel(val view: View): RecyclerView.ViewHolder(view)
}