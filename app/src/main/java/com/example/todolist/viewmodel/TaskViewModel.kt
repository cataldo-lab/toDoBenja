package com.example.todolist.viewmodel

import androidx.lifecycle.LiveData
import com.example.todolist.database.TaskDatabase
import com.example.todolist.model.Task
import com.example.todolist.model.TaskDao
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application){
    private val taskDao: TaskDao = TaskDatabase.getInstance(application).taskDao()

    val allTasks : LiveData<List<Task>> = taskDao.getAllTasks()

    fun insert(task: Task)= viewModelScope.launch{
        taskDao.insert(task)
    }

    fun deleteAll() = viewModelScope.launch{
        taskDao.deleteAllTasks()
    }
    fun toggleTaskCompletion(position: Int){
        // get the task by ID using the position as the identifier of the task
        val task = taskDao.getTaskById(position)

        // update the task
        taskDao.update(task)
    }

    fun onTaskCheckedChanged(){

    }
}