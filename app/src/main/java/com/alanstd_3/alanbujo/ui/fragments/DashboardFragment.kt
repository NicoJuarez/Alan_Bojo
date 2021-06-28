package com.alanstd_3.alanbujo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.database.repository.Repository
import com.alanstd_3.alanbujo.databinding.FragmentDashboardBinding
import com.alanstd_3.alanbujo.ui.dialogs.AddHabitDialog
import com.alanstd_3.alanbujo.ui.fragments.entities.habit.HabitAdapter

class DashboardFragment : GeneralFragment() {

    companion object {
        private const val TAG = "DASH_BRD:: "
    }

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
        configureButtons()

        return binding.root
    }

    private fun fillHabitList() {
        if (context == null)
            return

        val list = Repository(requireContext().applicationContext).getAllHabits()
        list?.let {
            val adapter = HabitAdapter(it)

            binding.habitsRecycler.apply {
                context?.let {
                    layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
                }
                this.adapter = adapter
            }
        }

    }

    private fun configureButtons() {
        binding.addHabitButton.setOnClickListener {
            buildHabitDialog()
        }
    }

    private fun buildHabitDialog() {
        val dialog = AddHabitDialog()

        dialog.listener = object : AddHabitDialog.OnSuccessListener {
            override fun onSuccess(habit: Habit) {
                saveHabit(habit)
            }
        }

        dialog.show(childFragmentManager, "")
    }

    private fun saveHabit(habit: Habit) {
        context?.let {
            Repository(it).saveHabit(habit)
            Log.d(TAG, "saveHabit| Saving | $habit ")
            binding.habitsRecycler.adapter?.update()
        }
    }




}