package com.rafaellyra.pinkyratasks.data.repository.impl.user

import com.rafaellyra.pinkyratasks.data.model.UserModel
import com.rafaellyra.pinkyratasks.eventbus.base.AppEventBus
import com.rafaellyra.pinkyratasks.eventbus.user.exception.UserFetchException
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFetchEvent
import org.greenrobot.eventbus.Subscribe

class UserRepository(databaseApi: UserDatabaseApi, retrofitApi: UserRetrofitApi) {
    init {
        AppEventBus.repository.register(this)
    }

    fun getUserWithEmail(email: String) {

    }

    fun createUser(userModel: UserModel) {

    }

    @Subscribe
    fun onUserFetch(event: UserFetchEvent) {

    }

    @Subscribe
    fun onFetchError(event: UserFetchException) {

    }
}
