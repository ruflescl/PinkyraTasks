package com.rafaellyra.pinkyratasks.data.repository.api.user

import com.rafaellyra.pinkyratasks.data.model.UserModel

interface UserApi {
    fun getUserWithEmailAndPassword(email: String, password: String)
    fun createUser(userModel: UserModel)
}