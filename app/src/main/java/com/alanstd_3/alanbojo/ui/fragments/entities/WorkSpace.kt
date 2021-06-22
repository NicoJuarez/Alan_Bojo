package com.alanstd_3.alanbojo.ui.fragments.entities

import com.alanstd_3.alanbojo.database.entities.Task
import com.alanstd_3.alanbojo.database.entities.Work

/**
 * Class representation of Work Space View._
 */
data class WorkSpace(
    var title: String = "Default",
    var parent: String = "General",
    var subtitle: String = "Subtitle",
    var tasks: List<Task> = mutableListOf(),
    var works: List<Work> = mutableListOf()
)