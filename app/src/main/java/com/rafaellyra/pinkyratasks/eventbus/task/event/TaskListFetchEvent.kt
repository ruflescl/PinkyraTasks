package com.rafaellyra.pinkyratasks.retrofit.task.event

import com.rafaellyra.pinkyratasks.data.model.TaskModel

data class TaskListFetchEvent(val data: List<TaskModel>)