package com.alanstd_3.alanbujo.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Work
import com.alanstd_3.alanbujo.database.repository.BUJODataBase
import com.alanstd_3.alanbujo.databinding.ActivityMainBinding
import com.alanstd_3.alanbujo.ui.dialogs.AddWorkSpaceDialog
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.dialog_add_work_space)

        window?.let {
            it.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.alan_primary)
        }

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
            }



            dialog.show(supportFragmentManager, "")
        }
    }

    private fun configureList() {
        binding.list.adapter = buildAdapter()
    }

    private fun getAllWorkSpaces(): List<String> {
        val list = mutableListOf<String>()

        val db =
            Room.databaseBuilder(applicationContext, BUJODataBase::class.java, BUJODataBase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

        runBlocking {
            val allWorks = db.workDao().getAll()
            if (allWorks != null && allWorks.isNotEmpty()) {
                for (work in allWorks) {
                    list.add(work.title)
                }
            }

        }

        return list
    }

    private fun saveNewWorkSpace(work: Work) {

        val db =
            Room.databaseBuilder(applicationContext, BUJODataBase::class.java, BUJODataBase.DB_NAME)
                .build()

        runBlocking {
            db.workDao().insert(work)
        }

        binding.list.adapter = buildAdapter()


    }

    private fun buildAdapter(): ArrayAdapter<String> {
        return ArrayAdapter(
            applicationContext, android.R.layout.simple_selectable_list_item,
            getAllWorkSpaces()
        )
    }

}