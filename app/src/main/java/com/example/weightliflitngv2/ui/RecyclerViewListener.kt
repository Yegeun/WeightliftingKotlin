package com.example.weightliflitngv2.ui

import android.view.View
import com.example.weightliflitngv2.data.Athlete

interface RecyclerViewListener{
    fun itemClicked(view: View, athlete: Athlete)
}

