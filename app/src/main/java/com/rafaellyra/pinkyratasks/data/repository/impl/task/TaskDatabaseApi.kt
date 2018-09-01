package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskDeleteException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskPersistException
import com.rafaellyra.pinkyratasks.room.dao.task.TaskDao

class TaskDatabaseApi(private val taskDao: TaskDao) : TaskApi {
    override fun getTask(id: Long): TaskModel? {
        try {
            return taskDao.get(id)
        }
        catch (e: Exception) {
            throw TaskFetchException(e)
        }
    }

    override fun getTasksFromUser(userModel: UserModel): List<TaskModel>? {
        try {
            return taskDao.getTasksFromUser(userModel.id)
        }
        catch (e: Exception) {
            throw TaskFetchException(e)
        }
    }

    override fun createTask(taskModel: TaskModel): TaskModel? {
        try {
            val id = taskDao.insert(taskModel)
            return taskDao.get(id)
        }
        catch (e: Exception) {
            throw TaskPersistException(e)
        }
    }

    override fun updateTask(taskModel: TaskModel): TaskModel {
        try {
            taskDao.update(taskModel)
            return taskModel
        }
        catch (e: Exception) {
            throw TaskPersistException(e)
        }
    }

    override fun deleteTask(taskModel: TaskModel): Long {
        try {
            taskDao.delete(taskModel)
            return taskModel.id
        }
        catch (e: Exception) {
            throw TaskDeleteException(e)
        }
    }
}