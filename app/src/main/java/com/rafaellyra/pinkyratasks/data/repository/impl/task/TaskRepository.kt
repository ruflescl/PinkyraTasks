package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.eventbus.base.AppEventBus
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFetchEvent
import org.greenrobot.eventbus.Subscribe

class TaskRepository(databaseApi: TaskDatabaseApi, retrofitApi: TaskRetrofitApi) {
    init {
        AppEventBus.repository.register(this)
    }

    fun getTask(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getTasksFromUser(userModel: UserModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createTask(taskModel: TaskModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun updateTask(taskModel: TaskModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteTask(taskModel: TaskModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Subscribe
    fun onTaskFetch(event: TaskFetchEvent) {

    }

    @Subscribe
    fun onFetchError(event: TaskFetchException) {

    }
}
