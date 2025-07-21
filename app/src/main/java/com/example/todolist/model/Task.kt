package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    val userId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val isCompleted: Boolean? = false,
    val completed: Boolean? = null
)
