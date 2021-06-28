package com.alanstd_3.alanbujo.ui.fragments.entities.habit

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.databinding.VhHabitBinding
import kotlin.random.Random

class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var id: Long = 0

    fun fillContent(habit: Habit) {
        val binding = VhHabitBinding.bind(itemView)

        id = habit.id

        binding.name.text = habit.title
        binding.imgBg.backgroundTintList = randomColor()
    }

    private fun randomColor(): ColorStateList? {
        return itemView.context.applicationContext?.let {
            val ran = Random(System.currentTimeMillis())
            when (ran.nextInt(0, 4)) {
                0 -> return@let ContextCompat.getColorStateList(it, R.color.alan_blue)
                1 -> return@let ContextCompat.getColorStateList(it, R.color.alan_red)
                2 -> return@let ContextCompat.getColorStateList(it, R.color.alan_yellow)
                3 -> return@let ContextCompat.getColorStateList(it, R.color.alan_grape)
                else -> return@let ContextCompat.getColorStateList(it, R.color.white)
            }
        }

    }

}