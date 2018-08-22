package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskDeleteException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskPersistException
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitResponseHandlerBase
import com.rafaellyra.pinkyratasks.retrofit.task.TaskRetrofitService
import retrofit2.Retrofit

class TaskRetrofitApi(private val retrofitConfig: Retrofit):
        RetrofitResponseHandlerBase<TaskModel>(),
        TaskApi {

    private val taskService = retrofitConfig.create(TaskRetrofitService::class.java)

    override fun getTask(id: Long): TaskModel? {
        try {
            return taskService.getById(id).execute().body()
        } catch (e: Exception) {
            throw TaskFetchException(e)
        }
    }

    override fun getTasksFromUser(userModel: UserModel): List<TaskModel>? {
        try {
            return taskService.listAll(userModel.id).execute().body()
        } catch (e: Exception) {
            throw TaskFetchException(e)
        }
    }

    override fun createTask(taskModel: TaskModel): TaskModel? {
        try {
            return taskService.create(taskModel).execute().body()
        } catch (e: Exception) {
            throw TaskPersistException(e)
        }
    }

    override fun updateTask(taskModel: TaskModel): TaskModel {
        try {
            taskService.update(taskModel).execute()
            return taskModel
        } catch (e: Exception) {
            throw TaskPersistException(e)
        }
    }

    override fun deleteTask(taskModel: TaskModel): Long {
        try {
            taskService.delete(taskModel.id).execute()
            return taskModel.id
        } catch (e: Exception) {
            throw TaskDeleteException(e)
        }
    }
}

