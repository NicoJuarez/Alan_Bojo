package com.alanstd_3.alanbujo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.repository.Repository
import com.alanstd_3.alanbujo.databinding.FragmentHabitBinding

class HabitFragment : GeneralFragment() {

    private lateinit var binding: FragmentHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitBinding.bind(
            inflater.inflate(R.layout.fragment_habit, container, false)
        )

        fillContent()

        return binding.root
    }

    private fun fillContent() {

        val habitID = arguments?.getLong("habit", -1L)

        if (habitID == -1L)
            return

        context?.let{
            val repo = Repository(it.applicationContext)
            repo.
        }

    }


}