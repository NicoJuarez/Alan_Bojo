package com.alanstd_3.alanbujo.ui.activities

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
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
import com.alanstd_3.alanbujo.ui.dialogs.AddWorkSpaceDialog
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.let {
            it.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.alan_primary)
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
//        return ArrayAdapter(
//            applicationContext, android.R.layout.simple_selectable_list_item,
//            getAllWorkSpaces()
//        )

        return MyAdapter(applicationContext, R.layout.item_work_space, repository.getAllWorks())

    }

    class MyAdapter(context: Context, resID: Int, list: List<Work>) :
        ArrayAdapter<Work>(context, resID, list) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_work_space, parent, false)

            val workItem = getItem(position)
            workItem?.let { work ->
                view.findViewById<TextView>(R.id.title).text = work.title
                view.findViewById<TextView>(R.id.subtitle).text = work.subtitle
                view.findViewById<TextView>(R.id.description).text = work.color
                context?.let {
                    if(work.color.isNotEmpty())
                    view.findViewById<CardView>(R.id.card_ws).setCardBackgroundColor(
//                        ContextCompat.getColorStateList(
//                            it.applicationContext,
//                            Color.parseColor(work.color)
//                        )
                        ColorStateList.valueOf(Color.parseColor(work.color))
                    )

                }
            }
            return view
        }

    }

}