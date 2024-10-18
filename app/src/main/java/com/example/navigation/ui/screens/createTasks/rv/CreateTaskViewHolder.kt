package com.example.navigation.ui.screens.createTasks.rv

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.data.models.Task
import com.example.navigation.databinding.TaskViewBinding

class CreateTaskViewHolder (
    private val binding: TaskViewBinding,
    private val onCheckClickListener: (id: Int) -> Unit,
    private val onTaskDetailClickListener: (id: Int) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(task: Task) {
        with(binding){
            tvTaskTitle.text = task.title
            cbIsCompleted.isChecked = task.isChecked

            cbIsCompleted.setOnClickListener{
                onCheckClickListener(task.id)
            }

            root.setOnClickListener{
                onTaskDetailClickListener(task.id)
            }
        }
    }
}