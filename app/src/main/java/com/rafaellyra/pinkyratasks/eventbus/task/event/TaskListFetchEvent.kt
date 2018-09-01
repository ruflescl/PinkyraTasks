package com.rafaellyra.pinkyratasks.eventbus.task.event

import com.rafaellyra.pinkyratasks.data.model.TaskModel

data class TaskListFetchEvent(val data: List<TaskModel>)