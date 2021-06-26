package com.alanstd_3.alanbujo.ui.dialogs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.repository.Repository
import com.alanstd_3.alanbujo.databinding.DialogAddTaskBinding

class AddTaskDialog(private val parentID: Long) : BaseDialog() {

    private lateinit var binding: DialogAddTaskBinding
    private var parentTitle = ""
    var listener: OnSubmitListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddTaskBinding.bind(
            inflater.inflate(R.layout.dialog_add_task, container, false)
        )

        configureButtons()
        presetParent()

        return binding.root
    }

    private fun configureButtons() {
        binding.addTaskButton.setOnClickListener { addTaskToList() }
        binding.submitButton.setOnClickListener { submit() }
    }

    private fun presetParent() {
        context?.let {
            val work = Repository(it).getWorkByID(parentID)
            if (work != null) {
                if (work.title.isNotEmpty()) {
                    parentTitle = work.title
                }
            }
        }
    }

    private fun addTaskToList() {
        val view = layoutInflater.inflate(R.layout.item_simple_task, null)
        view.findViewById<TextView>(R.id.description).text =
            binding.inputTaskDescription.text.toString()
        view.findViewById<TextView>(R.id.parent).apply {
            text = this@AddTaskDialog.parentTitle
            visibility = View.VISIBLE
        }
        binding.listTasks.addView(view)
        binding.inputTaskDescription.text = null
        Handler(Looper.getMainLooper()).postDelayed({
            binding.scroll.fullScroll(View.FOCUS_DOWN)
        }, 250)
    }

    private fun submit() {
        listener?.onSubmit(getTaskList())
        dialog?.dismiss()
    }

    private fun getTaskList(): List<Task> {
        val tasks = mutableListOf<Task>()

        for (child in binding.listTasks.children) {
            val task = Task()
            task.description = child.findViewById<TextView>(R.id.description).text.toString()
            task.parent = parentID
            task.date = "today :D"

            tasks.add(task)
        }

        return tasks
    }

    override fun onStart() {
        super.onStart()
        binding.inputTaskDescription.requestFocus()
        binding.inputTaskDescription.isClickable = true
        binding.inputTaskDescription.performClick()
    }

    interface OnSubmitListener {
        fun onSubmit(tasks: List<Task>)
    }

}