package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.user.UserApi
import com.rafaellyra.pinkyratasks.eventbus.base.AppEventBus
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserPersistException
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFailureEvent
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFetchEvent
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserPersistEvent
import com.rafaellyra.pinkyratasks.room.dao.user.UserDao

class UserDatabaseApi(val userDao: UserDao) : UserApi {
    override fun getUserWithEmail(email: String) {
        try {
            AppEventBus.repository.post(UserFetchEvent(userDao.getWithEmail(email)))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(UserFailureEvent(UserFetchException(e)))
        }
    }

    override fun createUser(userModel: UserModel) {
        try {
            userDao.insert(userModel)
            val insertedUser = userDao.getWithEmail(userModel.email)
            AppEventBus.repository.post(UserPersistEvent(insertedUser))
        }
        catch (e: Exception) {
            AppEventBus.repository.post(UserFailureEvent(UserPersistException(e)))
        }
    }
}
