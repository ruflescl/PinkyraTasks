package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.eventbus.user.event.UserAuthenticationFailEvent
import com.rafaellyra.pinkyratasks.eventbus.user.event.UserAuthenticationSuccessEvent
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserCreationEmptyDataException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchNoDataException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserPersistException
import com.rafaellyra.pinkyratasks.eventbus.user.event.UserFailureEvent
import com.rafaellyra.pinkyratasks.eventbus.user.event.UserPersistEvent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.EventBus

class UserRepository(private val databaseApi: UserDatabaseApi, private val retrofitApi: UserRetrofitApi) {
    fun authenticate(email: String) = async(CommonPool) {
        var result: UserModel?

        try {
            result = databaseApi.getUserWithEmail(email)

            if (result == null) {
                result = retrofitApi.getUserWithEmail(email)

                if (result != null) {
                    databaseApi.createUser(result)
                }
            }

            when (result) {
                null -> EventBus.getDefault().post(UserAuthenticationFailEvent(UserFetchNoDataException()))
                else -> EventBus.getDefault().post(UserAuthenticationSuccessEvent(result))
            }
        }
        catch (e: UserFetchException) {
            EventBus.getDefault().post(UserAuthenticationFailEvent(e))
        }
    }

    fun createUser(userModel: UserModel) = async(CommonPool) {
        val result: UserModel?

        try {
            result = retrofitApi.createUser(userModel)

            when(result) {
                null -> throw UserPersistException(UserCreationEmptyDataException())
                else -> databaseApi.createUser(result)
            }

            EventBus.getDefault().post(UserPersistEvent(result))
        }
        catch (e: UserPersistException) {
            EventBus.getDefault().post(UserFailureEvent(e))
        }
    }
}
