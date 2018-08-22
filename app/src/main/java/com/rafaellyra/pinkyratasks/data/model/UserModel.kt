package com.rafaellyra.pinkyratasks.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "User", indices = arrayOf(
        Index(value="email", unique = true)))
data class UserModel(@PrimaryKey(autoGenerate = true) val id: Long,
                     @NonNull val name: String,
                     @NonNull val username: String,
                     @NonNull val email: String)