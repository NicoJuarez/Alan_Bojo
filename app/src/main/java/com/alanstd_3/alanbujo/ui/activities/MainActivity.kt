package com.alanstd_3.alanbujo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.entities.Work
import com.alanstd_3.alanbujo.database.repository.BUJODataBase
import com.alanstd_3.alanbujo.databinding.ActivityMainBinding
import com.alanstd_3.alanbujo.databinding.DialogAddWorkSpaceBinding
import com.alanstd_3.alanbujo.ui.dialogs.AddWorkSpaceDialog
import com.alanstd_3.alanbujo.ui.fragments.WorkSpaceFragment
import com.alanstd_3.alanbujo.ui.fragments.entities.WorkSpace
import kotlinx.coroutines.Dispatchers
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


    }

    private fun configureButtons(){
        binding.addWsButton.setOnClickListener {
            val dialog = AddWorkSpaceDialog()

            dialog.show(supportFragmentManager, "")
        }
    }

}