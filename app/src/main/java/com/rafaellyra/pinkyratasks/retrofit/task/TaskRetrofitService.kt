package com.rafaellyra.pinkyratasks.retrofit.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import retrofit2.Call
import retrofit2.http.*

interface TaskRetrofitService {
    @GET("posts")
    fun listAll(): Call<List<TaskModel>>

    @GET("todos/{id}")
    fun getById(@Path("id") id: Long): Call<List<TaskModel>>

    @PUT("todos")
    fun updateOrCreate(@Body task: TaskModel): Call<TaskModel>

    @DELETE("todos/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}