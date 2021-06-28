package com.alanstd_3.alanbujo.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Habit
import com.alanstd_3.alanbujo.database.repository.Repository
import com.alanstd_3.alanbujo.databinding.FragmentHabitBinding

class HabitFragment : GeneralFragment() {

    companion object {
        private const val TAG = "HABIT::"
    }

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

        arguments?.let { bundle ->
            val args = HabitFragmentArgs.fromBundle(bundle)
            if (args.id == 0L)
                return@let


            context?.let {
                val repo = Repository(it.applicationContext)
                habit = repo.getHabit(args.id)

//            habit = Habit(
//                title = "Drink water!!", description = "#DrinkWaterChallenge",
//                minGoal = 2, goal = 5, extra = 0, currentDone = 3
//            )

                habit?.let { h ->
                    binding.title.text = h.title
                    binding.description.text = h.description

                    fillItems(h.minGoal, h.goal - h.minGoal, h.extra)
                    setChecked(h.currentDone)
                }
            }
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
        val i = getItemsSelected()
        saveCurrentData(i)

        // probando seleccionados
        context?.let {
            Toast.makeText(
                it, "Seleccionados: $i",
                Toast.LENGTH_SHORT
            ).show()
            setChecked(i)
        }

        binding.submitButton.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().popBackStack()
        }, 1000)


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
            normalColor()
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
                if (!i.isSelected)
                    i.callOnClick()
            }
        }
    }

    private fun normalColor() {
        habit?.let {
            for (i in 0 until itemList.size) {
                if (i < it.minGoal)
                    context?.let { c ->
                        itemList[i].imageTintList =
                            ContextCompat.getColorStateList(c, R.color.alan_blue)
                    }
                else {
                    context?.let { c ->
                        itemList[i].imageTintList =
                            ContextCompat.getColorStateList(c, R.color.white)
                    }
                    Log.d(TAG, "normalColor: count: $i")
                }

            }
        }
    }

    private fun saveCurrentData(count: Int) {

        context?.let { c ->
            val repo = Repository(c)
            habit?.let { h ->
                h.currentDone = count
                repo.updateHabit(h)
                Log.d(TAG, "saveCurrentData: $h")
            }
        }

        if (habit == null) {
            Log.d(TAG, "saveCurrentData: (No se guardó)")
        }

    }


}