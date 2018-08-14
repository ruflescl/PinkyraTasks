package com.rafaellyra.pinkyratasks.retrofit.user.event

import com.rafaellyra.pinkyratasks.data.model.UserModel

data class UserFetchEvent(val data: List<UserModel>, val adapterPosition: Int = -1)