package com.alanstd_3.alanbujo.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
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
                    return repository.workExists(title)
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

    private fun buildAdapter(): ArrayAdapter<String> {
        return ArrayAdapter(
            applicationContext, android.R.layout.simple_selectable_list_item,
            getAllWorkSpaces()
        )
    }

}