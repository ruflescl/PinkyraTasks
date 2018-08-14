package com.rafaellyra.pinkyratasks.data.model

import com.rafaellyra.pinkyratasks.data.repository.api.IBaseEntity

class TaskModel(
        val id: Long,
        val userId: Long,
        val title: String,
        val completed: Boolean = false,
        val body: String = "",
        var user: UserModel): IBaseEntity {

    override fun id(): Long {
        return id
    }
}