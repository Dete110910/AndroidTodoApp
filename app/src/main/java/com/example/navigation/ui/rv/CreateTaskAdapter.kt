package com.example.navigation.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.data.entities.TaskEntity
import com.example.navigation.databinding.TaskViewBinding

class CreateTaskAdapter(
    entryTaskList: List<TaskEntity>,
    private val onCheckClickListener: (id: Int) -> Unit,
    private val onDeleteClickListener: (id: Int) -> Unit,
    private val onTaskDetailClickListener: (id: Int) -> Unit
) : RecyclerView.Adapter<CreateTaskViewHolder>() {

    private var taskList : List<TaskEntity> = entryTaskList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateTaskViewHolder {
        val binding = TaskViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CreateTaskViewHolder(
            binding = binding,
            onCheckClickListener = onCheckClickListener,
            onDeleteClickListener = onDeleteClickListener,
            onTaskDetailClickListener = onTaskDetailClickListener
        )
    }

    override fun onBindViewHolder(holder: CreateTaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size

    fun updateTaskList(taskList: List<TaskEntity>) {
        this.taskList = taskList
    }
}