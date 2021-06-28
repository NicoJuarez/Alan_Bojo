package com.alanstd_3.alanbujo.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.databinding.DialogCreateHabitBinding
import com.alanstd_3.alanbujo.general.ColorUtils
import java.util.*

class AddHabitDialog(private val habit: Habit = Habit()) : BaseDialog() {

    private lateinit var binding: DialogCreateHabitBinding
    var listener: OnSuccessListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCreateHabitBinding.bind(
            inflater.inflate(R.layout.dialog_create_habit, container, false)
        )

        configureInitialUse()
        configureButtons()

        return binding.root
    }

    private fun configureInitialUse() {
        binding.habitName.requestFocus()

        habit.apply {
            if (title.isNotBlank()) binding.habitName.setText(title)
            if (goal != 0) binding.goal.setText(goal.toString())
            if (minGoal != 0) binding.minGoal.setText(minGoal.toString())

        }
    }

    private fun configureButtons() {
        binding.submitButtom.setOnClickListener { submit() }
    }

    private fun submit() {
        if (validateFields()) {
            listener?.onSuccess(createHabit())
            dialog?.dismiss()
        }
    }

    private fun validateFields(): Boolean {

        return valName() && valGoals()
    }

    private fun valName(): Boolean {
        return if (binding.habitName.text.isBlank()) {
            binding.habitName.apply {
                requestFocus()
                this.error = getString(R.string.text_empty_field)
            }
            false
        } else {
            binding.habitName.apply {
                this.error = null
            }
            true
        }
    }

    private fun valGoals(): Boolean {
        val goal = binding.goal.text.toString()
        val minGoal = binding.minGoal.text.toString()

        return if (goal.isBlank()/* || minGoal.isBlank()*/) {

            if (goal.isBlank()) {
                binding.goal.apply {
                    error = getString(R.string.text_empty_field)
                    requestFocus()
                }
            }
            false
        } else {

            binding.goal.apply {
                error = null
            }

            binding.minGoal.apply {
                error = null
            }

            return if (minGoal.isNotBlank() && minGoal.toInt() >= goal.toInt()) {
                binding.minGoal.apply {
                    this.requestFocus()
                    error = getString(R.string.text_min_goal_goal)
                }
                false
            } else {

                binding.minGoal.apply {
                    error = null
                }
                true
            }
        }
    }

    private fun createHabit(): Habit {
        val h = Habit()

        h.title = binding.habitName.text.toString()
        h.goal = binding.goal.text.toString().toInt()
        if (binding.minGoal.text.toString().isNotBlank())
            h.minGoal = binding.minGoal.text.toString().toInt()
        else
            h.minGoal = 0
        context?.let {
            h.color = ColorUtils.getColorHex(ContextCompat.getColor(it, R.color.alan_grape))
        }
        h.date = Calendar.getInstance().timeInMillis

        return h
    }

    interface OnSuccessListener {
        fun onSuccess(habit: Habit)
    }


}