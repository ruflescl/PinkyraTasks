package com.rafaellyra.pinkyratasks.retrofit.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitService {
    @GET("users")
    fun getByEmail(@Query("email") email: String): Call<UserModel>

    @POST("users")
    fun create(@Body item: UserModel): Call<UserModel>
}