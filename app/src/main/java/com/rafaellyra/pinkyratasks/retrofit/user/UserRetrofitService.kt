package com.rafaellyra.pinkyratasks.retrofit.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitService {
    @GET("users")
    fun listAll(): Call<List<UserModel>>

    @GET("users/{id}")
    fun getById(@Path("id") id: Long): Call<UserModel>

    @PUT("users")
    fun updateOrCreate(@Body item: UserModel): Call<UserModel>

    @DELETE("users/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}