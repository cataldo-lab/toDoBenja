package com.example.todolist.data

import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val api: ToDoApi
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> = localDataSource.getAll()

    override suspend fun addTask(task: Task) {
        localDataSource.insert(task)
    }

    override suspend fun deleteTask(task: Task) {
        localDataSource.delete(task)
    }

    override suspend fun syncTasks() {
        val remoteTasks = api.getTodos()
        // Guardamos algunos datos de ejemplo de la API
        remoteTasks.take(10).forEach {
            val task = Task(
                title = it.title ?: "Sin título",
                description = "Importado desde API",
                isCompleted = it.completed ?: false
            )
            localDataSource.insert(task)
        }
    }
}
