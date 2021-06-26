package com.alanstd_3.alanbujo.database.repository

import android.content.Context
import androidx.room.Room
import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.entities.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class Repository(context: Context) {

    /**
     * Cambiar implementación una vez que se aprenda migración._
     */
    private val db = Room.databaseBuilder(
        context.applicationContext, BUJODataBase::class.java, BUJODataBase.DB_NAME
    ).fallbackToDestructiveMigration().build()

    fun saveNewWork(work: Work): Long {
        return runBlocking(Dispatchers.Default) {
            val dao = db.workDao()
            return@runBlocking if (dao.exists(work.title))
                -1
            else
                dao.insert(work)
        }
    }

    fun getAllWorks(): List<Work> {
        return runBlocking(Dispatchers.Default) {
            return@runBlocking db.workDao().getAll()
        }
    }

    fun workExists(title: String): Boolean {
        return runBlocking(Dispatchers.Default) {
            return@runBlocking db.workDao().exists(title)
        }
    }

    fun getWorkByTitle(title: String): Work {
        return runBlocking(Dispatchers.Default) {
            return@runBlocking db.workDao().getByTitle(title)
        }
    }

    fun getWorkByID(id: Long): Work {
        return runBlocking(Dispatchers.Default) {
            return@runBlocking db.workDao().getByID(id)
        }
    }

    fun saveNewTasks(tasks: List<Task>) {
        runBlocking {
            val dao = db.taskDao()
            for (t in tasks)
                dao.insert(t)
        }
    }

    fun getAllTasksByParent(parentID: Long): List<Task> {
        return runBlocking {
            val dao = db.taskDao()
            return@runBlocking dao.getByParent(parentID)
        }
    }

}