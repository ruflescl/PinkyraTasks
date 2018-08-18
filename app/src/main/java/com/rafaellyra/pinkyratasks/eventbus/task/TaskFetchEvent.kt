package com.rafaellyra.pinkyratasks.retrofit.task.event

import com.rafaellyra.pinkyratasks.data.model.TaskModel

data class TaskFetchEvent(val data: List<TaskModel>, val adapterPosition: Int = -1)