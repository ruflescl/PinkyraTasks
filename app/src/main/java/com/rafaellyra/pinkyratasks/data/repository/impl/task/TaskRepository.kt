package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskFetchNoDataException
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskSingleFetchEvent
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFailureEvent
import org.greenrobot.eventbus.EventBus

class TaskRepository(val databaseApi: TaskDatabaseApi, val retrofitApi: TaskRetrofitApi) {

    fun getTask(id: Long) {
        var result: TaskModel?

        try {
            result = databaseApi.getTask(id)

            if (result == null) {
                result = retrofitApi.getTask(id)

                if (result != null) {
                    databaseApi.createTask(result)
                }
            }
        }
        catch (e: TaskFetchException) {
            EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(e)))
            return
        }

        when (result) {
            null -> EventBus.getDefault().post(TaskFailureEvent(TaskFetchNoDataException()))
            else -> EventBus.getDefault().post(TaskSingleFetchEvent(result))
        }
    }

    fun getTasksFromUser(userModel: UserModel) {
        var databaseResult: List<TaskModel>?
        var retrofitResult: List<TaskModel>?
        var result: List<TaskModel>

        try {
            databaseResult = databaseApi.getTasksFromUser(userModel)
            retrofitResult = retrofitApi.getTasksFromUser(userModel)

            // TODO: Fazer a diferença e inserir os novos
            if (databaseResult != null && retrofitResult != null) {
                if (!databaseResult.containsAll(retrofitResult)) {

                }
            }
        }
        catch (e: TaskFetchException) {
            EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(e)))
            return
        }

        when (databaseResult) {
            null -> EventBus.getDefault().post(TaskFailureEvent(TaskFetchNoDataException()))
            else -> EventBus.getDefault().post(TaskSingleFetchEvent(databaseResult))
        }
    }

    fun createTask(taskModel: TaskModel) {

    }

    fun updateTask(taskModel: TaskModel) {

    }

    fun deleteTask(taskModel: TaskModel) {

    }
}
