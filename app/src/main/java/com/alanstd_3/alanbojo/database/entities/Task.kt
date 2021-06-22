package com.alanstd_3.alanbojo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Task.TABLE_TASK)
data class Task(
    @ColumnInfo(name = DESCRIPTION)
    var description: String = "",
    @ColumnInfo(name = DATE)
    var date: String = "",
    @ColumnInfo(name = IS_CHECKED)
    var isChecked: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Long = 0

    @ColumnInfo(name = PARENT)
    var parent: Long = 0

    companion object {
        const val TABLE_TASK = "table_tasks"
        const val ID = "_id"
        const val DESCRIPTION = "desc"
        const val DATE = "date"
        const val PARENT = "fk_work"
        const val IS_CHECKED = "is_checked"
    }

}