package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskSingleFetchEvent
import com.rafaellyra.pinkyratasks.eventbus.task.exception.*
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskDeleteEvent
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskFailureEvent
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskListFetchEvent
import com.rafaellyra.pinkyratasks.eventbus.task.event.TaskPersistEvent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.EventBus

class TaskRepository(private val databaseApi: TaskDatabaseApi, private val retrofitApi: TaskRetrofitApi) {

    fun getTask(id: Long) = async(CommonPool) {
        var result: TaskModel?

        try {
            result = databaseApi.getTask(id)

            if (result == null) {
                result = retrofitApi.getTask(id)

                if (result != null) {
                    databaseApi.createTask(result)
                }
            }

            when (result) {
                null -> EventBus.getDefault().post(TaskFailureEvent(TaskFetchNoDataException()))
                else -> EventBus.getDefault().post(TaskSingleFetchEvent(result))
            }
        }
        catch (e: TaskFetchException) {
            EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(e)))
        }
    }

    fun getTasksFromUser(userModel: UserModel) = async(CommonPool) {
        val databaseResult: List<TaskModel>?
        val retrofitResult: List<TaskModel>?
        var result: List<TaskModel>? = null

        try {
            databaseResult = databaseApi.getTasksFromUser(userModel)
            retrofitResult = retrofitApi.getTasksFromUser(userModel)

            if (databaseResult != null && retrofitResult != null) {
                if (!databaseResult.containsAll(retrofitResult)) {
                    val diffListFromNetwork: List<TaskModel> = retrofitResult.minus(databaseResult)
                    val diffListFromLocal: List<TaskModel> = databaseResult.minus(retrofitResult)

                    if (diffListFromNetwork.isNotEmpty())
                        diffListFromNetwork.forEach { task -> databaseApi.createTask(task) }

                    if (diffListFromLocal.isNotEmpty())
                        diffListFromLocal.forEach { task -> retrofitApi.createTask(task) }

                    result = diffListFromLocal.plus(diffListFromNetwork)
                }
                else {
                    result = databaseResult
                }
            }

            when (result) {
                null -> EventBus.getDefault().post(TaskFailureEvent(TaskFetchNoDataException()))
                else -> EventBus.getDefault().post(TaskListFetchEvent(result))
            }
        }
        catch (e: TaskFetchException) {
            EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(e)))
        }
    }

    fun createTask(taskModel: TaskModel) = async(CommonPool) {
        val result: TaskModel?

        try {
            result = retrofitApi.createTask(taskModel)
            databaseApi.createTask(taskModel)

            when (result) {
                null -> EventBus.getDefault().post(TaskFailureEvent(TaskPersistNotCreatedException()))
                else -> EventBus.getDefault().post(TaskPersistEvent(result))
            }
        }
        catch (e: Exception) {
            EventBus.getDefault().post(TaskFailureEvent(TaskPersistException(e)))
        }

    }

    fun updateTask(taskModel: TaskModel) = async(CommonPool) {
        val result: TaskModel?

        try {
            result = retrofitApi.updateTask(taskModel)
            databaseApi.updateTask(taskModel)

            when(result) {
                null -> EventBus.getDefault().post(TaskFailureEvent(TaskPersistNotUpdatedException()))
                else -> EventBus.getDefault().post(TaskPersistEvent(result))
            }
        }
        catch (e: Exception) {
            EventBus.getDefault().post(TaskFailureEvent(TaskPersistException(e)))
        }
    }

    fun deleteTask(taskModel: TaskModel) = async(CommonPool) {
        val result: Long

        try {
            result = retrofitApi.deleteTask(taskModel)
            databaseApi.deleteTask(taskModel)

            EventBus.getDefault().post(TaskDeleteEvent(result))
        }
        catch (e: Exception) {
            EventBus.getDefault().post(TaskFailureEvent(TaskDeleteException(e)))
        }
    }
}
