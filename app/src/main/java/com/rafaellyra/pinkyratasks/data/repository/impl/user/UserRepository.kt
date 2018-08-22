package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.eventbus.user.UserAuthenticationFailEvent
import com.rafaellyra.pinkyratasks.eventbus.user.UserAuthenticationSuccessEvent
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserCreationEmptyDataException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchNoDataException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserPersistException
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserPersistEvent
import org.greenrobot.eventbus.EventBus

class UserRepository(private val databaseApi: UserDatabaseApi, private val retrofitApi: UserRetrofitApi) {
    fun authenticate(email: String) {
        var result: UserModel?

        try {
            result = databaseApi.getUserWithEmail(email)

            if (result == null) {
                result = retrofitApi.getUserWithEmail(email)

                if (result != null) {
                    databaseApi.createUser(result)
                }
            }
        }
        catch (e: UserFetchException) {
            EventBus.getDefault().post(UserAuthenticationFailEvent(e))
            return
        }

        when (result) {
            null -> EventBus.getDefault().post(UserAuthenticationFailEvent(UserFetchNoDataException()))
            else -> EventBus.getDefault().post(UserAuthenticationSuccessEvent(result))
        }
    }

    fun createUser(userModel: UserModel) {
        val result: UserModel?

        try {
            result = retrofitApi.createUser(userModel)

            when(result) {
                null -> throw UserPersistException(UserCreationEmptyDataException())
                else -> databaseApi.createUser(result)
            }
        }
        catch (e: UserPersistException) {
            EventBus.getDefault().post(UserFailureEvent(e))
            return
        }

        EventBus.getDefault().post(UserPersistEvent(result))
    }
}
