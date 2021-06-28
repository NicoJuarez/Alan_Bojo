package com.alanstd_3.alanbujo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = Habit.TABLE_HABITS)
data class Habit(
    @ColumnInfo(name = TITLE)
    var title: String = "",
    @ColumnInfo(name = DESCRIPTION)
    var description: String = "",
    @ColumnInfo(name = MIN_GOAL)
    var minGoal: Int = 0,
    @ColumnInfo(name = GOAL)
    var goal: Int = 0,
    @ColumnInfo(name = EXTRA)
    var extra: Int = 0,
    @ColumnInfo(name = CURRENT_DONE)
    var currentDone: Int = 0,
    @ColumnInfo(name = NOTE_1)
    var note1: String = "",
    @ColumnInfo(name = NOTE_2)
    var note2: String = "",
    @ColumnInfo(name = NOTE_3)
    var note3: String = "",
    @ColumnInfo(name = DATE)
    var date: Long = 0,
    @ColumnInfo(name = COLOR)
    var color: String = "",
    @ColumnInfo(name = DAYS)    //1,2,3,4,5,6,7 ; where 1 => SUNDAY...
    var days: String = "",
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
        const val DATE = "date"
        const val COLOR = "color"
        const val DAYS = "days"

    }

    override fun toString(): String {
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMdd - hh:mm:ss")
        c.timeInMillis = date
        return "HABIT => ${title.uppercase()}| Desc: $description; {goal: $goal; minGoal: $minGoal; " +
                "current: $currentDone; extra: $extra; color: $color; date: ${sdf.format(c.time)}}" +
                "\nnote1: $note1\nnote2: $note2\nnote3: $note3"
    }

    /**
     * Changes only elemental information, maintaining date, id...
     */
    fun copyElementalValues(newHabit: Habit) {
        color = newHabit.color
        goal = newHabit.goal
        minGoal = newHabit.minGoal
        title = newHabit.title
        description = newHabit.description
        if (currentDone > goal) {
            currentDone = goal
        }
    }

    /**
     * Reset the values like currentCount, extras, in function of currentDay._
     */
    fun cleanIfNeeds() {
        if (days.isNotBlank()) {
            val aDays = days.split(",")
            val calendar = Calendar.getInstance()
            val currentDay = calendar.get(Calendar.DAY_OF_WEEK)

            var i = 0
            while (i < aDays.size && currentDay != aDays[i].toInt()) {
                i++
            }

            if (i < aDays.size)
                forceClean()

        } else {
            forceClean()
        }

    }

    fun forceClean() {
        currentDone = 0
        extra = 0
    }
}