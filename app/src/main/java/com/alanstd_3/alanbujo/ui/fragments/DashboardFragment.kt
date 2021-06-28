package com.alanstd_3.alanbujo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.databinding.FragmentDashboardBinding
import com.alanstd_3.alanbujo.ui.fragments.entities.habit.HabitAdapter

class DashboardFragment : GeneralFragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.bind(
            inflater.inflate(R.layout.fragment_dashboard, container, false)
        )

        fillHabitList()

        return binding.root
    }

    private fun fillHabitList() {
        val list = mutableListOf<Habit>()
        for (i in 0..9) {
            val habit = Habit()
            list.add(habit)
        }
        val adapter = HabitAdapter(list)

        binding.habitsRecycler.apply {
            context?.let {
                layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            }
            this.adapter = adapter
        }

    }


}