package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.*
import com.example.todolist.database.TaskDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks = TaskDatabase.getDatabase(application).taskDao().getAll().asLiveData()

    init {
        val dao = TaskDatabase.getDatabase(application).taskDao()
        val localSource = LocalDataSource(dao)
        repository = TaskRepositoryImpl(localSource, RetrofitClient.api)
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.addTask(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun toggleTaskCompletion(position: Int) = viewModelScope.launch {
        allTasks.value?.let { tasks ->
            if (position < tasks.size) {
                val task = tasks[position]
                val updatedTask = task.copy(isCompleted = !(task.isCompleted ?: false))
                repository.updateTask(updatedTask)
            }
        }
    }

    fun onTaskCheckedChanged(task: Task, position: Int) = viewModelScope.launch {
        val updatedTask = task.copy(isCompleted = !(task.isCompleted ?: false))
        repository.updateTask(updatedTask)
    }

    fun sync() = viewModelScope.launch {
        repository.syncTasks()
    }
}