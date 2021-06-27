package com.alanstd_3.alanbujo.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.databinding.FragmentHabitBinding

class HabitFragment : GeneralFragment() {

    private lateinit var binding: FragmentHabitBinding
    private val itemList = mutableListOf<ImageView>()
    private var habit: Habit? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitBinding.bind(
            inflater.inflate(R.layout.fragment_habit, container, false)
        )

        fillContent()
        configureButtons()

        return binding.root
    }

    private fun fillContent() {

//        val habitID = arguments?.getLong("habit", -1L)
//
//        if (habitID == null || habitID == -1L)
//            return

        context?.let {
//            val repo = Repository(it.applicationContext)
//            val habit = repo.getHabit(habitID)

//            binding.title.text = habit.title
//            binding.description.text = habit.description
//            fillItems(minCount, goalCount, extraCount)
            habit = Habit()

            binding.title.text = "Drink water!!"
            binding.description.text = "#DinkWaterChallenge"
            val minCount = 2
            val goal = 5
            val extra = 0
            val currentDone = 5

            fillItems(minCount, goal - minCount, extra)
            setChecked(currentDone)
        }

    }

    private fun fillItems(minCount: Int, goalCount: Int, extraCount: Int) {
        for (i in 1..(minCount + goalCount + extraCount)) {
            val item = ImageView(context)
            val params = LinearLayout.LayoutParams(70, 70)
            params.setMargins(8, 0, 8, 0)
            item.layoutParams = params

            item.setImageResource(R.drawable.ic_circle_24)
            item.imageTintList = when {
                (i <= minCount) -> context?.let {
                    ContextCompat.getColorStateList(it, R.color.alan_blue)
                }

                (i in (minCount + 1)..(minCount + goalCount)) -> context?.let {
                    ContextCompat.getColorStateList(it, R.color.white)
                }

                else -> context?.let {
                    ContextCompat.getColorStateList(it, R.color.alan_yellow)
                }
            }
            item.setOnClickListener {
                it.isSelected = !it.isSelected
                if (it.isSelected) {
                    item.setImageResource(R.drawable.ic_check_circle_24)
                } else {
                    item.setImageResource(R.drawable.ic_circle_24)
                }
            }

            // añadimos los items
            itemList.add(item)
            binding.itemsList.addView(item)


        }
    }

    private fun configureButtons() {
        binding.addNoteButton.setOnClickListener {
            showAddNoteDialog()
        }

        binding.submitButton.setOnClickListener {
            submit()
        }
    }

    private fun showAddNoteDialog() {
        //  TODO: Crear diálogo para cargar notas._
    }

    private fun submit() {
        //  TODO: Guardar los dato en la memoria._

        // probando seleccionados
        context?.let {
            val i = getItemsSelected()
            Toast.makeText(
                it, "Seleccionados: $i",
                Toast.LENGTH_SHORT
            ).show()
            setChecked(i)
        }


    }

    private fun getItemsSelected(): Int {
        var count = 0

        for (item in itemList) {
            if (item.isSelected)
                count++
        }

        return count
    }

    private fun setChecked(count: Int) {

        if (count > itemList.size) {
            addExtraItems(count - itemList.size)
        } else if (count < itemList.size) {
            for (i in 0 until itemList.size) {
//                itemList[i].isSelected = (i <= count)
                if (i < count && !itemList[i].isSelected)
                    itemList[i].callOnClick()
                else if (i >= count && itemList[i].isSelected)
                    itemList[i].callOnClick()
            }
        } else {
            goalAccomplished()
        }

    }

    private fun addExtraItems(extraCount: Int) {

    }

    private fun goalAccomplished() {
        for (i in itemList) {
            context?.let {
                i.imageTintList = ContextCompat.getColorStateList(it, R.color.alan_yellow)
                if(!i.isSelected)
                    i.callOnClick()
            }
        }
    }


}