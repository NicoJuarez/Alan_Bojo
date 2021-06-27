package com.alanstd_3.alanbujo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Habit.TABLE_HABITS)
data class Habit(
    @ColumnInfo(name = TITLE)
    var title: String = "",
    @ColumnInfo(name = DESCRIPTION)
    var description: String = "",
    @ColumnInfo(name = MIN_GOAL)
    var minGoal: String = "",
    @ColumnInfo(name = GOAL)
    var goal: String = "",
    @ColumnInfo(name = EXTRA)
    var extra: String = "",
    @ColumnInfo(name = CURRENT_DONE)
    var currentDone: String = "",
    @ColumnInfo(name = NOTE_1)
    var note1: String = "",
    @ColumnInfo(name = NOTE_2)
    var note2: String = "",
    @ColumnInfo(name = NOTE_3)
    var note3: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Long = 0

    @ColumnInfo(name = PARENT_ID)
    var workSpaceParentID: Long = 0

    companion object {
        const val TABLE_HABITS = "table_habits"
        const val ID = "_id"
        const val PARENT_ID = "parent_id"
        const val TITLE = "title"
        const val DESCRIPTION = "desc"
        const val MIN_GOAL = "min_goal"
        const val GOAL = "goal"
        const val EXTRA = "extra"
        const val CURRENT_DONE = "current"
        const val NOTE_1 = "n_1"
        const val NOTE_2 = "n_2"
        const val NOTE_3 = "n_3"

    }
}