package com.example.todolist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()

    @Query("SELECT * FROM tasks WHERE _id = :id")
    fun getTaskById(id: Int) : Task
}