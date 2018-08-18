package com.rafaellyra.pinkyratasks.data.repository.api.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel

interface TaskApi {
    fun getTask(id: Long)
    fun getTasksFromUser(userModel: UserModel)
    fun getTasksFromUserId(userId: Long)
    fun createTask(taskModel: TaskModel)
    fun updateTask(taskModel: TaskModel)
    fun deleteTask(id: Long)
}