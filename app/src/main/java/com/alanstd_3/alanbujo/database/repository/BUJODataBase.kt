package com.alanstd_3.alanbujo.database.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alanstd_3.alanbujo.database.dao.TaskDAO
import com.alanstd_3.alanbujo.database.dao.WorkDAO
import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.entities.Work

@Database(
    entities = [Task::class, Work::class],
    version = 2,
    exportSchema = false
)
abstract class BUJODataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDAO
    abstract fun workDao(): WorkDAO

    companion object {
        const val DB_NAME = "_bujo_database"
    }
}