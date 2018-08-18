package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskDeleteException
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskPersistException
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitResponseHandlerBase
import com.rafaellyra.pinkyratasks.retrofit.task.TaskRetrofitService
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskDeleteEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFetchEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskPersistEvent
import org.greenrobot.eventbus.EventBus
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
        getTasksFromUserId(userModel.id)
    }

    override fun getTasksFromUserId(userId: Long) {
        taskService.listAll(userId).enqueue(createListFetchResponseHandler())
    }

    override fun createTask(taskModel: TaskModel) {
        taskService.create(taskModel).enqueue(createSingleFetchResponseHandler())
    }

    override fun updateTask(taskModel: TaskModel) {
        taskService.update(taskModel).enqueue(createSingleFetchResponseHandler())
    }

    override fun deleteTask(id: Long) {
        taskService.delete(id).enqueue(createDeleteResponseHandler(id))
    }

    override fun createSingleFetchResponseHandler(adapterPosition: Int): Callback<TaskModel> {
        return object: Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(t)))
            }

            override fun onResponse(call: Call<TaskModel>?, response: Response<TaskModel>?) {
                EventBus.getDefault().post(TaskFetchEvent(createListFromResponse(response),
                        adapterPosition))
            }
        }
    }

    override fun createListFetchResponseHandler(): Callback<List<TaskModel>> {
        return object: Callback<List<TaskModel>> {
            override fun onFailure(call: Call<List<TaskModel>>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(t)))
            }

            override fun onResponse(call: Call<List<TaskModel>>?, response: Response<List<TaskModel>>?) {
                EventBus.getDefault().post(TaskFetchEvent(response?.body() ?: ArrayList<TaskModel>()))
            }
        }
    }

    override fun createPersistResponseHandler(): Callback<TaskModel> {
        return object : Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskPersistException(t)))
            }

            override fun onResponse(call: Call<TaskModel>?, response: Response<TaskModel>?) {
                EventBus.getDefault().post(TaskPersistEvent(response?.body()))
            }
        }
    }

    override fun createDeleteResponseHandler(id: Long): Callback<Void> {
        return object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskDeleteException(t)))
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                EventBus.getDefault().post(TaskDeleteEvent(id))
            }
        }
    }
}

