package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.model.Task
import com.example.todolist.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupRecyclerView()
        binding.buttonAddtask.setOnClickListener{
            val newTask = Task(
                title = binding.taskTitle.text.toString(),
                description = binding.taskDescription.text.toString(),
                isCompleted = false
            )
            viewModel.insert(newTask)
        }
    }

    fun setupRecyclerView(){
        val adapter = TaskAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.allTasks.observe(this){
            allTasks -> adapter.setItems(allTasks)
        }
    }
}
