package com.example.todolist.data

import com.example.todolist.model.Task
import retrofit2.http.GET

interface ToDoApi {
    @GET("todos")
    suspend fun getTodos(): List<Task>
}
