package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val title: String,
    val description: String?,
    var isCompleted: Boolean
)