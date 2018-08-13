package com.rafaellyra.pinkyratasks.data.repository.impl.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.repository.api.IBaseRepository
import com.rafaellyra.pinkyratasks.retrofit.task.*
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskDeleteEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFetchEvent
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskPersistEvent
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskDeleteException
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskPersistException
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TaskRetrofitRepository(private val retrofitConfig: Retrofit): IBaseRepository<TaskModel> {

    private val taskService: TaskRetrofitService = retrofitConfig.create(TaskRetrofitService::class.java)

    private fun createFetchResponseHandler(): Callback<List<TaskModel>> {
        return object: Callback<List<TaskModel>> {
            override fun onFailure(call: Call<List<TaskModel>>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskFetchException(t)))
            }

            override fun onResponse(call: Call<List<TaskModel>>?, response: Response<List<TaskModel>>?) {
                EventBus.getDefault().post(TaskFetchEvent(response?.body() ?: ArrayList<TaskModel>()))
            }
        }
    }

    private fun createPersistResponseHandler(): Callback<TaskModel> {
        return object : Callback<TaskModel> {
            override fun onFailure(call: Call<TaskModel>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskPersistException(t)))
            }

            override fun onResponse(call: Call<TaskModel>?, response: Response<TaskModel>?) {
                EventBus.getDefault().post(TaskPersistEvent(response?.body()))
            }
        }
    }

    private fun createDeleteResponseHandler(id: Long): Callback<Void> {
        return object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                EventBus.getDefault().post(TaskFailureEvent(TaskDeleteException(t)))
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                EventBus.getDefault().post(TaskDeleteEvent(id))
            }
        }
    }

    override fun get(id: Long) {
        taskService.getById(id).enqueue(createFetchResponseHandler())
    }

    override fun list() {
        taskService.listAll().enqueue(createFetchResponseHandler())
    }

    override fun persist(item: TaskModel) {
        taskService.updateOrCreate(item).enqueue(createPersistResponseHandler())
    }

    override fun delete(id: Long) {
        taskService.delete(id).enqueue(createDeleteResponseHandler(id))
    }
}

