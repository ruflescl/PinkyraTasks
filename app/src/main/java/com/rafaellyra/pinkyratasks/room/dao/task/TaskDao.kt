package com.rafaellyra.pinkyratasks.room.dao.task

import android.arch.persistence.room.*
import com.rafaellyra.pinkyratasks.data.model.TaskModel

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun list(): List<TaskModel>?

    @Query("SELECT * FROM Task WHERE id = :id")
    fun get(id: Long): TaskModel?

    @Query("SELECT * FROM Task WHERE userId = :userId")
    fun getTasksFromUser(userId: Long): List<TaskModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: TaskModel): Long

    @Update
    fun update(task: TaskModel)

    @Delete
    fun delete(task: TaskModel)
}
