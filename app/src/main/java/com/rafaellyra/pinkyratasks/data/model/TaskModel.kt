package com.rafaellyra.pinkyratasks.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "Task",
        foreignKeys =
            arrayOf(
                    ForeignKey(entity = UserModel::class,
                            parentColumns = arrayOf("id"),
                            childColumns = arrayOf("userId"),
                            onDelete = ForeignKey.CASCADE)))
data class TaskModel(
        @PrimaryKey(autoGenerate = true) val id: Long,
        @NonNull val userId: Long,
        @NonNull val title: String,
        @NonNull val completed: Boolean = false)