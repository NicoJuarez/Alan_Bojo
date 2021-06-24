package com.alanstd_3.alanbujo.ui.fragments.entities

import com.alanstd_3.alanbujo.database.entities.Task
import com.alanstd_3.alanbujo.database.entities.Work

/**
 * Class representation of Work Space View._
 */
data class WorkSpace(
    var title: String = "Default",
    var parent: String = "General",
    var subtitle: String = "Subtitle",
    var tasks: List<Task> = mutableListOf(),
    var works: List<Work> = mutableListOf()
) {
    constructor(work: Work) : this(work, mutableListOf(), mutableListOf())
    constructor(work: Work, tasks: List<Task>, works: List<Work>) : this(
        title = work.title, subtitle = work.subtitle,
        tasks = tasks, works = works
    )
}