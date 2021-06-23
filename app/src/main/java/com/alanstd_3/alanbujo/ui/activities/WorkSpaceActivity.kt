package com.alanstd_3.alanbujo.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.entities.Work
import com.alanstd_3.alanbujo.database.repository.BUJODataBase
import com.alanstd_3.alanbujo.ui.fragments.WorkSpaceFragment
import com.alanstd_3.alanbujo.ui.fragments.entities.WorkSpace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class WorkSpaceActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_space)

        window?.let {
            it.statusBarColor = ContextCompat.getColor(this@WorkSpaceActivity, R.color.alan_primary)
        }


//        val database =
//            Room.databaseBuilder(applicationContext, BUJODataBase::class.java, BUJODataBase.DB_NAME)
//                .build()
//
//        val workDao = database.workDao()
//        val taskDao = database.taskDao()
//
//        runBlocking(Dispatchers.Default) {
//            val parentID =
//                workDao.insert(Work("Fiverr", "dibujos", "Trabajos relacionados al dibujo"))
//            workDao.insert(
//                Work("Mobile", "apps", "Desarrollo de aplicaciones móviles en android")
//            )
//            workDao.insert(Work("WebComics", "", "Creación de historietas en formato vertical"))
//
//            taskDao.insert(Task("Lineart work 35"))
//            taskDao.insert(Task("Bocetos Leo"))
//            val mTask = Task("Actualizar precio")
//            mTask.parent = parentID
//            taskDao.insert(mTask)
//        }

        val database =
            Room.databaseBuilder(applicationContext, BUJODataBase::class.java, BUJODataBase.DB_NAME)
                .build()
        val wDao = database.workDao()
        val tDao = database.taskDao()
        runBlocking (Dispatchers.Default){

            val work = wDao.getByTitle("Fiverr")
            work?.let{
                val tasks = tDao.getByParent(work[0].id)
                setFragment(tasks, null)
            }

        }

    }

    private fun setFragment(tasks: List<Task>, works: List<Work>?){
        val ws = WorkSpaceFragment(
            WorkSpace("Fiverr", "General/root/...", "", tasks)
        )
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true).add(R.id.container, ws, "ws").commit()
    }
}