package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.eventbus.base.AppEventBus
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskDeleteException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskPersistException
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskDeleteEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFetchEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskPersistEvent
import com.rafaellyra.pinkyratasks.room.dao.task.TaskDao

class TaskDatabaseApi(val taskDao: TaskDao) : TaskApi {
    override fun getTask(id: Long) {
        try {
            AppEventBus.repository.post(TaskFetchEvent(listOf(taskDao.get(id))))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(TaskFailureEvent(TaskFetchException(e)))
        }
    }

    override fun getTasksFromUser(userModel: UserModel) {
        try {
            AppEventBus.repository.post(TaskFetchEvent(taskDao.getTasksFromUser(userModel.id)))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(TaskFailureEvent(TaskFetchException(e)))
        }
    }

    override fun createTask(taskModel: TaskModel) {
        try {
            val id = taskDao.insert(taskModel)
            val insertedTask = taskDao.get(id)
            AppEventBus.repository.post(TaskPersistEvent(insertedTask))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(TaskFailureEvent(TaskPersistException(e)))
        }
    }

    override fun updateTask(taskModel: TaskModel) {
        try {
            taskDao.update(taskModel)
            AppEventBus.repository.post(TaskPersistEvent(taskModel))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(TaskFailureEvent(TaskPersistException(e)))
        }
    }

    override fun deleteTask(taskModel: TaskModel) {
        try {
            taskDao.delete(taskModel)
            AppEventBus.repository.post(TaskDeleteEvent(taskModel.id))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(TaskFailureEvent(TaskDeleteException(e)))
        }
    }
}