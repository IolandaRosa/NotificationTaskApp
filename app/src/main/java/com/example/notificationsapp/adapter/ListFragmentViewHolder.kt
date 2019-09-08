package com.example.notificationsapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationsapp.R

class ListFragmentViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val viewImageFlow = v.findViewById<ImageView>(R.id.listViewFluxView)
    val viewNameText = v.findViewById<TextView>(R.id.listViewTask)
    val viewDateText = v.findViewById<TextView>(R.id.listViewDate)
    val viewHourText = v.findViewById<TextView>(R.id.listViewHour)

}
