package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.model.Task
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.worker.SyncWorker

class MainActivity : ComponentActivity() {
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding + ViewModel
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()

        // Botón para agregar tarea localmente
        binding.buttonAddtask.setOnClickListener {
            val title = binding.taskTitle.text.toString().trim()
            val description = binding.taskDescription.text.toString().trim()

            if (title.isNotEmpty()) {
                val newTask = Task(
                    title = title,
                    description = description,
                    isCompleted = false
                )
                viewModel.insert(newTask)

                // Limpiar campos
                binding.taskTitle.text.clear()
                binding.taskDescription.text.clear()
            }
        }

        // Disparar sincronización con API al iniciar
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()
        WorkManager.getInstance(this).enqueue(syncRequest)
    }

    private fun setupRecyclerView() {
        val adapter = TaskAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allTasks.observe(this) { allTasks ->
            adapter.setItems(allTasks)
        }
    }
}