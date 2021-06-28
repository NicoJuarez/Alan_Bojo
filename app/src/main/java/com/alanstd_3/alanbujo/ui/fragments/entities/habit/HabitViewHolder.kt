package com.alanstd_3.alanbujo.ui.fragments.entities.habit

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.databinding.VhHabitBinding

class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var id: Long = 0
    var listener: OnVHHabitListener? = null

    fun fillContent(habit: Habit) {
        val binding = VhHabitBinding.bind(itemView)

        id = habit.id

        binding.name.text = habit.title
        if (habit.color.isNotBlank())
            binding.imgBg.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(habit.color))

        when {
            habit.goal == habit.currentDone -> {
                binding.goalCaption.visibility = View.VISIBLE
            }
            (habit.minGoal!= 0) && (habit.minGoal <= habit.currentDone) ->{
                binding.goalCaption.visibility = View.VISIBLE
                itemView.context?.let{
                    binding.goalCaption.imageTintList=
                        ContextCompat.getColorStateList(it, R.color.alan_cream)
                }
            }
        }


        itemView.setOnClickListener { listener?.onClick(id) }
    }

//    private fun randomColor(): ColorStateList? {
//        return itemView.context.applicationContext?.let {
//            val ran = Random(System.currentTimeMillis())
//            when (ran.nextInt(0, 4)) {
//                0 -> return@let ContextCompat.getColorStateList(it, R.color.alan_blue)
//                1 -> return@let ContextCompat.getColorStateList(it, R.color.alan_red)
//                2 -> return@let ContextCompat.getColorStateList(it, R.color.alan_yellow)
//                3 -> return@let ContextCompat.getColorStateList(it, R.color.alan_grape)
//                else -> return@let ContextCompat.getColorStateList(it, R.color.white)
//            }
//        }
//
//    }


    interface OnVHHabitListener {
        fun onClick(id: Long)
    }

}