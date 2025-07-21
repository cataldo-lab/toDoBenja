package com.example.todolist.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todolist.data.RetrofitClient
import com.example.todolist.database.TaskDatabase
import com.example.todolist.model.Task

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val dao = TaskDatabase.getDatabase(applicationContext).taskDao()
        return try {
            val remoteTasks = RetrofitClient.api.getTodos()
            // Guarda los datos de la API en la base de datos
            remoteTasks.take(10).forEach { // opcional: tomar solo 10 para no llenar
                val task = Task(
                    title = it.title ?: "Sin título",
                    description = "Importado desde API",
                    isCompleted = it.completed ?: false
                )
                dao.insert(task)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
