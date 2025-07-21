package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.model.Task
import com.example.todolist.viewmodel.TaskViewModel

class TaskAdapter(private val viewModel: TaskViewModel): RecyclerView.Adapter<TaskAdapter.ToDoViewHolder>() {
    private var items: List<Task> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Task>){
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, viewModel, position)
    }

    override fun getItemCount(): Int = items.size

    class ToDoViewHolder(private val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task, viewModel: TaskViewModel, position: Int){
            binding.item = item
            binding.position = position
            binding.viewModel = viewModel

            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.isChecked = item.isCompleted
            binding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                viewModel.toggleTaskCompletion(binding.position)
            }

            binding.executePendingBindings()
        }
    }
}