package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.data.repository.api.user.UserApi
import com.rafaellyra.pinkyratasks.eventbus.base.AppEventBus
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserDeleteException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserPersistException
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitResponseHandlerBase
import com.rafaellyra.pinkyratasks.retrofit.user.UserRetrofitService
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserDeleteEvent
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFetchEvent
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserPersistEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserRetrofitApi(private val retrofitConfig: Retrofit): RetrofitResponseHandlerBase<UserModel>(),
        UserApi {

    private val userService = retrofitConfig.create(UserRetrofitService::class.java)

    override fun getUserWithEmail(email: String) {
        userService.getByEmail(email).enqueue(createSingleFetchResponseHandler())
    }

    override fun createUser(userModel: UserModel) {
        userService.create(userModel).enqueue(createSingleFetchResponseHandler())
    }

    private fun createSingleFetchResponseHandler(): Callback<UserModel> {
        return object: Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                AppEventBus.repository.post(UserFailureEvent(UserFetchException(t)))
            }

            override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                AppEventBus.repository.post(UserFetchEvent(response?.body()!!))
            }
        }
    }
}