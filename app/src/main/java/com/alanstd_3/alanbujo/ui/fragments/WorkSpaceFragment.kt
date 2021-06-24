package com.alanstd_3.alanbujo.ui.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.entities.Work
import com.alanstd_3.alanbujo.databinding.FragmentWorkSpaceBinding
import com.alanstd_3.alanbujo.ui.fragments.entities.WorkSpace
import com.google.android.material.snackbar.Snackbar


class WorkSpaceFragment(private val workSpace: WorkSpace = WorkSpace()) : GeneralFragment() {

    constructor(work: Work) : this(WorkSpace(work))
    constructor(work: Work, tasks: List<Task>, works: List<Work>) : this(
        WorkSpace(work, tasks, works)
    )

    private var _binding: FragmentWorkSpaceBinding? = null
    private val binding: FragmentWorkSpaceBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_work_space, null)
        _binding = FragmentWorkSpaceBinding.bind(
            view
        )

        fillWorkSpace()

        return view
    }

    private fun fillWorkSpace() {
        binding.title.text = workSpace.title
        if (workSpace.subtitle.isNotBlank()) {
            binding.subtitle.text = workSpace.subtitle
        } else {
            binding.subtitle.visibility = View.GONE
        }
        binding.root.text = workSpace.parent

        fillTasks()
        fillWorks()

        configureButtons()
    }

    private fun fillTasks() {
        if (workSpace.tasks.isEmpty()) {
            binding.tasksList.addView(
                layoutInflater.inflate(R.layout.item_empty, null)
            )
        } else {
            for (work in workSpace.tasks) {
                val taskView = layoutInflater.inflate(R.layout.item_task, null)

                val text = taskView.findViewById<TextView>(R.id.text)
                val check = taskView.findViewById<CheckBox>(R.id.check)
                text.text = work.description

                check.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked)
                        text.paintFlags =
                            text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                    else
                        text.paintFlags = text.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    //text.paintFlags = text.paintFlags
                }

                text.setOnClickListener {
                    check.isChecked = !check.isChecked
                }

                binding.tasksList.addView(taskView)
            }
        }
    }

    private fun fillWorks() {
        if (workSpace.works.isEmpty()) {
            binding.worksList.addView(
                layoutInflater.inflate(R.layout.item_empty, null)
            )
        } else {
            for (work in workSpace.works) {
                val workView = layoutInflater.inflate(R.layout.item_work_space, null)
                val title = workView.findViewById<TextView>(R.id.title)
                val subtitle = workView.findViewById<TextView>(R.id.subtitle)
                if (work.subtitle.isNotBlank()) {
                    title.text = work.title + ":"
                    subtitle.text = work.subtitle
                } else {
                    title.text = work.title
                }
                workView.findViewById<TextView>(R.id.description).text = work.description

                workView.setOnClickListener {
                    if (subtitle.text.isBlank())
                        workTouched(title.text.toString())
                    else
                        workTouched(
                            title.text.toString().substring(0, title.text.toString().length - 1)
                        )
                }

                binding.worksList.addView(workView)
            }
        }
    }

    private fun configureButtons() {

        binding.addButton.setOnClickListener {
            showDefaultSnackBar("Agregando items owo", binding.root, Snackbar.LENGTH_SHORT)
        }

        binding.backButton.setOnClickListener {
            showDefaultSnackBar(
                "Volviendo a vista principal nwn", binding.root, Snackbar.LENGTH_SHORT
            )
        }

        binding.submitButton.setOnClickListener {
            showDefaultSnackBar(
                "Actualizando tareas y trabajos :3", binding.root, Snackbar.LENGTH_SHORT
            )
        }

        binding.exitButton.setOnClickListener {
            showDefaultSnackBar(
                "Saliendo uwu", binding.root, Snackbar.LENGTH_SHORT
            )
        }

    }

    private fun workTouched(text: String) {
        showFunnyMessage(text, binding.toastContainer)
    }


}