package com.alanstd_3.alanbojo.database.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alanstd_3.alanbojo.database.dao.TaskDAO
import com.alanstd_3.alanbojo.database.dao.WorkDAO
import com.alanstd_3.alanbojo.database.entities.Task
import com.alanstd_3.alanbojo.database.entities.Work

@Database(entities = [Task::class, Work::class], version = 1, exportSchema = false)
abstract class BUJODataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDAO
    abstract fun workDao(): WorkDAO

    companion object {
        const val DB_NAME = "_bujo_database"
    }
}