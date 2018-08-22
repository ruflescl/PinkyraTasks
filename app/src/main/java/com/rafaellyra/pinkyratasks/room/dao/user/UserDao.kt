package com.rafaellyra.pinkyratasks.room.dao.user

import android.arch.persistence.room.*
import com.rafaellyra.pinkyratasks.data.model.UserModel

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun list(): List<UserModel>

    @Query("SELECT * FROM User WHERE id = :id")
    fun get(id: Long): UserModel

    @Query("SELECT * FROM User WHERE email = :email")
    fun getWithEmail(email: String): UserModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserModel): Long

    @Update
    fun update(user: UserModel)

    @Delete
    fun delete(user: UserModel)
}