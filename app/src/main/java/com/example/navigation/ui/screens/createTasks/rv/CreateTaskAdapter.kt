package com.example.navigation.ui.screens.createTasks.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.data.models.Task
import com.example.navigation.databinding.TaskViewBinding

class CreateTaskAdapter(
    private val entryTaskList : List<Task>,
    private val onCheckClickListener: (id: Int) -> Unit,
) : RecyclerView.Adapter<CreateTaskViewHolder>() {

    var taskList : List<Task> = entryTaskList

    fun updateTaskList(taskList: List<Task>){
        this.taskList = taskList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateTaskViewHolder {
        val binding = TaskViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        Log.d("TEST", "Lista al inicio: ${taskList}")
        return CreateTaskViewHolder(
            binding = binding,
            onCheckClickListener = onCheckClickListener
        )
    }

    override fun onBindViewHolder(holder: CreateTaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size
}