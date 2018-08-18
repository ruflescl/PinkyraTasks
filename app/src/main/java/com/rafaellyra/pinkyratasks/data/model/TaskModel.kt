package com.rafaellyra.pinkyratasks.data.model

class TaskModel(
        val id: Long,
        val userId: Long,
        val title: String,
        val completed: Boolean = false){
}