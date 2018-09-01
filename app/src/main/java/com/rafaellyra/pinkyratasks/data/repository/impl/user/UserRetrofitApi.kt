package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.user.UserApi
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitResponseHandlerBase
import com.rafaellyra.pinkyratasks.retrofit.user.UserRetrofitService
import retrofit2.Retrofit

class UserRetrofitApi(retrofitConfig: Retrofit): RetrofitResponseHandlerBase<UserModel>(),
        UserApi {

    private val userService = retrofitConfig.create(UserRetrofitService::class.java)

    override fun getUserWithEmail(email: String): UserModel? {
        try {
            return userService.getByEmail(email).execute().body()
        } catch (e: Exception) {
            throw UserFetchException(e)
        }
    }

    override fun createUser(userModel: UserModel): UserModel? {
        try {
            return userService.create(userModel).execute().body()
        } catch (e: Exception) {
            throw UserFetchException(e)
        }
    }
}