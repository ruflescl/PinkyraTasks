package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.task.TaskApi
import com.rafaellyra.pinkyratasks.data.repository.api.user.UserApi
import com.rafaellyra.pinkyratasks.data.repository.impl.user.exception.UserDeleteException
import com.rafaellyra.pinkyratasks.data.repository.impl.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.data.repository.impl.user.exception.UserPersistException
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

    override fun getUserWithEmailAndPassword(email: String, password: String) {
        userService.getByEmail(email).enqueue(createSingleFetchResponseHandler())
    }

    override fun createUser(userModel: UserModel) {
        userService.create(userModel).enqueue(createSingleFetchResponseHandler())
    }

    override fun createSingleFetchResponseHandler(adapterPosition: Int): Callback<UserModel> {
        return object: Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                EventBus.getDefault().post(UserFailureEvent(UserFetchException(t)))
            }

            override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                val result: List<UserModel> = if (response?.body() != null) {
                    listOf(response?.body()!!)
                } else {
                    ArrayList<UserModel>()
                }

                EventBus.getDefault().post(UserFetchEvent(result,
                        adapterPosition))
            }
        }
    }

    override fun createListFetchResponseHandler(): Callback<List<UserModel>> {
        return object: Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {
                EventBus.getDefault().post(UserFailureEvent(UserFetchException(t)))
            }

            override fun onResponse(call: Call<List<UserModel>>?, response: Response<List<UserModel>>?) {
                EventBus.getDefault().post(UserFetchEvent(response?.body() ?: ArrayList<UserModel>()))
            }
        }
    }

    override fun createPersistResponseHandler(): Callback<UserModel> {
        return object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                EventBus.getDefault().post(UserFailureEvent(UserPersistException(t)))
            }

            override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                EventBus.getDefault().post(UserPersistEvent(response?.body()))
            }
        }
    }

    override fun createDeleteResponseHandler(id: Long): Callback<Void> {
        return object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                EventBus.getDefault().post(UserFailureEvent(UserDeleteException(t)))
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                EventBus.getDefault().post(UserDeleteEvent(id))
            }
        }
    }
}