package com.rafaellyra.pinkyratasks

import android.app.Application
import com.rafaellyra.pinkyratasks.data.model.UserModel

class CustomApplication: Application() {
    lateinit var user: UserModel
        private set

    fun setupUser(user: UserModel) {
        this.user = user
    }
}