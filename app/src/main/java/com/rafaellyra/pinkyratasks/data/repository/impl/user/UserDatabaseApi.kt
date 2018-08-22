package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.data.repository.api.user.UserApi
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserPersistException
import com.rafaellyra.pinkyratasks.room.dao.user.UserDao

class UserDatabaseApi(val userDao: UserDao) : UserApi {
    override fun getUserWithEmail(email: String): UserModel? {
        try {
            return userDao.getWithEmail(email)
        }
        catch (e: Exception) {
            throw UserFetchException(e)
        }
    }

    override fun createUser(userModel: UserModel): UserModel {
        try {
            val id: Long = userDao.insert(userModel)
            return userDao.get(id)
        }
        catch (e: Exception) {
            throw UserPersistException(e)
        }
    }
}
