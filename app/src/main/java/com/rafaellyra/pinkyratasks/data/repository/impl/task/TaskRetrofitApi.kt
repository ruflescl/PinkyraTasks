package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.eventbus.base.AppEventBus
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskDeleteException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.eventbus.task.exception.TaskPersistException
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitResponseHandlerBase
import com.rafaellyra.pinkyratasks.retrofit.task.TaskRetrofitService
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskDeleteEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFetchEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskPersistEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TaskRetrofitApi(private val retrofitConfig: Retrofit):
        RetrofitResponseHandlerBase<TaskModel>(),
        TaskApi {

    private val taskService = retrofitConfig.create(TaskRetrofitService::class.java)

    override fun getTask(id: Long) {
        taskService.getById(id).enqueue(createSingleFetchResponseHandler())
    }

    override fun getTasksFromUser(userModel: UserModel) {
        taskService.listAll(userModel.id).enqueue(createListFetchResponseHandler())
    }

    override fun createTask(taskModel: TaskModel) {
        taskService.create(taskModel).enqueue(createPersistResponseHandler())
    }

    override fun updateTask(taskModel: TaskModel) {
        taskService.update(taskModel).enqueue(createSingleFetchResponseHandler())
    }

    override fun deleteTask(taskModel: TaskModel) {
        taskService.delete(taskModel.id).enqueue(createDeleteResponseHandler(taskModel.id))
    }

    private fun createSingleFetchResponseHandler(): Callback<TaskModel> {
        return object: Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>?, t: Throwable?) {
                AppEventBus.repository.post(TaskFailureEvent(TaskFetchException(t)))
            }

            override fun onResponse(call: Call<TaskModel>?, response: Response<TaskModel>?) {
                AppEventBus.repository.post(TaskFetchEvent(createListFromResponse(response)))
            }
        }
    }

    private fun createListFetchResponseHandler(): Callback<List<TaskModel>> {
        return object: Callback<List<TaskModel>> {
            override fun onFailure(call: Call<List<TaskModel>>?, t: Throwable?) {
                AppEventBus.repository.post(TaskFailureEvent(TaskFetchException(t)))
            }

            override fun onResponse(call: Call<List<TaskModel>>?, response: Response<List<TaskModel>>?) {
                AppEventBus.repository.post(TaskFetchEvent(response?.body() ?: ArrayList<TaskModel>()))
            }
        }
    }

    private fun createPersistResponseHandler(): Callback<TaskModel> {
        return object : Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>?, t: Throwable?) {
                AppEventBus.repository.post(TaskFailureEvent(TaskPersistException(t)))
            }

            override fun onResponse(call: Call<TaskModel>?, response: Response<TaskModel>?) {
                AppEventBus.repository.post(TaskPersistEvent(response?.body()!!))
            }
        }
    }

    private fun createDeleteResponseHandler(id: Long): Callback<Void> {
        return object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                AppEventBus.repository.post(TaskFailureEvent(TaskDeleteException(t)))
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                AppEventBus.repository.post(TaskDeleteEvent(id))
            }
        }
    }
}

