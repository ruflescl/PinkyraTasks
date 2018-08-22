package com.rafaellyra.pinkyratasks.data.repository.api.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel

interface TaskApi {
    fun getTask(id: Long): TaskModel?
    fun getTasksFromUser(userModel: UserModel): List<TaskModel>?
    fun createTask(taskModel: TaskModel): TaskModel?
    fun updateTask(taskModel: TaskModel): TaskModel
    fun deleteTask(taskModel: TaskModel): Long
}