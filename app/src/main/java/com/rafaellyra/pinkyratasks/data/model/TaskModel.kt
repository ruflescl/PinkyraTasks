package com.rafaellyra.pinkyratasks.data.model

import com.rafaellyra.pinkyratasks.data.repository.api.IBaseEntity

data class TaskModel(
        val id: Long,
        val userId: Long,
        val title: String,
        val body: String = "",
        val completed: Boolean = false): IBaseEntity {

    override fun id(): Long {
        return id
    }
}