package com.alanstd_3.alanbujo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alanstd_3.alanbujo.database.entities.Habit

@Dao
interface HabitDAO {

    @Insert
    suspend fun insert(habit: Habit): Long

    @Update
    suspend fun update(habit: Habit)

    @Query("delete from ${Habit.TABLE_HABITS} where _id = :id")
    suspend fun delete(id: Long)

    @Query("delete from ${Habit.TABLE_HABITS} where parent_id = :wsParentID")
    suspend fun deleteByParent(wsParentID: Long)

    @Query("select * from ${Habit.TABLE_HABITS} where parent_id = :wsParentID")
    suspend fun getByParent(wsParentID: Long): List<Habit>

    @Query("select * from ${Habit.TABLE_HABITS} ")
    suspend fun getAll(): List<Habit>

    @Query("select * from ${Habit.TABLE_HABITS} where _id = :id")
    suspend fun getById(id: Long): Habit

    @Query("select * from ${Habit.TABLE_HABITS} where enabled = 1")
    suspend fun getAllEnabled(): List<Habit>

}