package com.alanstd_3.alanbujo.database.dao

import androidx.room.*
import com.alanstd_3.alanbujo.database.entities.Work

@Dao
interface WorkDAO {

    @Insert
    suspend fun insert(work: Work): Long

    @Update
    suspend fun update(work: Work)

//    @Delete
//    suspend fun delete(task: Work)

    @Query("delete from ${Work.TABLE_WORK} where _id = :id")
    suspend fun delete(id: Long)

    @Query("select * from ${Work.TABLE_WORK} where fk_work = :parentID")
    suspend fun getByParent(parentID: Long): List<Work>

    @Query("select * from ${Work.TABLE_WORK} where title = :title")
    suspend fun getByTitle(title: String): Work

    @Query("select * from ${Work.TABLE_WORK} where _id = :id")
    suspend fun getByID(id: Long): Work

    @Query("select * from ${Work.TABLE_WORK}")
    suspend fun getAll(): List<Work>

    @Query("select exists (select 1 from ${Work.TABLE_WORK} WHERE title = :workName)")
    suspend fun exists(workName: String): Boolean

}