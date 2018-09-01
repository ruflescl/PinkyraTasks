package com.rafaellyra.pinkyratasks.eventbus.user.event

import com.rafaellyra.pinkyratasks.data.model.UserModel

data class UserAuthenticationSuccessEvent(val data: UserModel)