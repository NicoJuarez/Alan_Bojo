package com.alanstd_3.alanbujo.ui.fragments.entities.habit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit

class HabitAdapter(private val habits: List<Habit>) : RecyclerView.Adapter<HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.fillContent(habits[position])
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    fun update(){
        notifyDataSetChanged()
        notif
    }
}