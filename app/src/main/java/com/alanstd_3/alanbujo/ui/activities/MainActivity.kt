package com.alanstd_3.alanbujo.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Work
import com.alanstd_3.alanbujo.database.repository.Repository
import com.alanstd_3.alanbujo.databinding.ActivityMainBinding
import com.alanstd_3.alanbujo.ui.dialogs.AddTaskDialog
import com.alanstd_3.alanbujo.ui.dialogs.AddWorkSpaceDialog
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository

    companion object {
        private const val TAG = "_MAIN_::"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.let {
            it.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.alan_default)
        }
        repository = Repository(applicationContext)

        configureButtons()
        configureList()


    }

    private fun configureButtons() {
        binding.addWsButton.setOnClickListener {
            val dialog = AddWorkSpaceDialog()

            dialog.listener = object : AddWorkSpaceDialog.OnSubmitListener {
                override fun onSubmitClick(work: Work) {
                    saveNewWorkSpace(work)
                }

                override fun canSaveWork(title: String): Boolean {
                    return !repository.workExists(title)
                }
            }

            dialog.show(supportFragmentManager, "")
        }
        binding.taskButton.setOnClickListener{
            val dialog = AddTaskDialog(4)

            dialog.show(supportFragmentManager, "")
        }
        binding.taskButton.visibility = View.GONE
    }

    private fun configureList() {
        binding.list.adapter = buildAdapter()
    }

    private fun getAllWorkSpaces(): List<String> {
        val list = mutableListOf<String>()

        runBlocking {
            val allWorks = repository.getAllWorks()
            if (allWorks != null && allWorks.isNotEmpty()) {
                for (work in allWorks) {
                    list.add(work.title)
                }
            } else {
                list.add(getString(R.string.text_empty))
            }
        }

        return list
    }

    private fun saveNewWorkSpace(work: Work) {
        repository.saveNewWork(work)
        binding.list.adapter = buildAdapter()
    }

    private fun buildAdapter(): MyAdapter {

        return MyAdapter(
            applicationContext, R.layout.item_work_space, repository.getAllWorks()
        ) {
            Log.d(TAG, "buildAdapter: touched!")
            val i = Intent(applicationContext, WorkSpaceActivity::class.java)
            val w = repository.getWorkByTitle(it.findViewById<TextView>(R.id.title).text.toString())
            i.putExtra("id", w.id)
            Log.d(TAG, "buildAdapter: id: ${w.id}")
            startActivity(i)
        }

    }

    class MyAdapter(
        context: Context, resID: Int, list: List<Work>,
        private val listener: View.OnClickListener
    ) :
        ArrayAdapter<Work>(context, resID, list) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work_space, parent, false)

            val workItem = getItem(position)
            workItem?.let { work ->
                view.findViewById<TextView>(R.id.title).text = work.title
                view.findViewById<TextView>(R.id.subtitle).text = work.subtitle
                view.findViewById<TextView>(R.id.description).text = work.color

                val cardView = view.findViewById<CardView>(R.id.card_ws)
                context?.let {
                    if (work.color.isNotEmpty())
                        cardView.setCardBackgroundColor(
                            ColorStateList.valueOf(Color.parseColor(work.color))
                        )
                }
                cardView.setOnClickListener(listener)
            }
            return view
        }

//        fun setListener(listener: View.OnClickListener){
//            getView
//        }

    }

}