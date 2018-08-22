package com.rafaellyra.pinkyratasks.data.repository.api.user

import com.rafaellyra.pinkyratasks.data.model.UserModel

interface UserApi {
    fun getUserWithEmail(email: String): UserModel?
    fun createUser(userModel: UserModel): UserModel?
}