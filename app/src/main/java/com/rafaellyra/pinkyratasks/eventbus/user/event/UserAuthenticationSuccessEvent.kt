package com.rafaellyra.pinkyratasks.eventbus.user

import com.rafaellyra.pinkyratasks.data.model.UserModel

data class UserAuthenticationSuccessEvent(val data: UserModel)