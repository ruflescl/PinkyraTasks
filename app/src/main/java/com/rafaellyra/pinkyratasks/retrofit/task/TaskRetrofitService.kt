package com.rafaellyra.pinkyratasks.retrofit.task

import com.rafaellyra.pinkyratasks.data.model.TaskModel
import retrofit2.Call
import retrofit2.http.*

interface TaskRetrofitService {
    @GET("todos")
    fun listAll(@Query("userId") userId: Long): Call<List<TaskModel>>

    @GET("todos/{id}")
    fun getById(@Path("id") id: Long): Call<TaskModel>

    @PUT("todos")
    fun update(@Body task: TaskModel): Call<TaskModel>

    @POST("todos")
    fun create(@Body task: TaskModel): Call<TaskModel>

    @DELETE("todos/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}