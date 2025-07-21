package com.example.todolist.data

import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun syncTasks()
}
