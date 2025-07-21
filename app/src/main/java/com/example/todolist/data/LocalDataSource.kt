package com.example.todolist.data

import com.example.todolist.database.TaskDao
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: TaskDao) {
    fun getAll(): Flow<List<Task>> = dao.getAll()

    suspend fun insert(task: Task) = dao.insert(task)

    suspend fun update(task: Task) = dao.update(task)

    suspend fun delete(task: Task) = dao.delete(task)
}