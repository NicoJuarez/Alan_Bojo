package com.alanstd_3.alanbojo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Work.TABLE_WORK)
data class Work(
    @ColumnInfo(name = TITLE)
    var title: String = "",
    @ColumnInfo(name = SUBTITLE)
    var subtitle: String = "",
    @ColumnInfo(name = DESCRIPTION)
    var description: String = "",

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Long = 0

    @ColumnInfo(name = PARENT)
    var parent: Long = 0


    companion object {
        const val TABLE_WORK = "table_works"
        const val ID = "_id"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val DESCRIPTION = "desc"
        const val PARENT = "fk_work"
    }
}
