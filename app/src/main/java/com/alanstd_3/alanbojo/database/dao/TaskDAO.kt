package com.alanstd_3.alanbojo.database.dao

import androidx.room.*
import com.alanstd_3.alanbojo.database.entities.Task

@Dao
interface TaskDAO {

    @Insert
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

//    @Delete
//    suspend fun delete(task: Task)//commented reason: competia con el @query ("delete ...")

    @Query("delete from ${Task.TABLE_TASK} where _id = :id")
    suspend fun delete(id: Long)

    @Query("delete from ${Task.TABLE_TASK} where fk_work = :parentID")
    suspend fun deleteByParent(parentID: Long)

    @Query("select * from ${Task.TABLE_TASK} where fk_work = :parentID")
    suspend fun getByParent(parentID: Long): List<Task>

    @Query("select * from ${Task.TABLE_TASK} where fk_work = :parentID and is_checked = :checked")
    suspend fun getByParentByStatus(parentID: Long, checked: Boolean): List<Task>

    @Query("select * from ${Task.TABLE_TASK} ")
    suspend fun getAll(): List<Task>
}