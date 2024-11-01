package com.example.navigation.ui.rv

import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.data.entities.TaskEntity
import com.example.navigation.databinding.TaskViewBinding

class CreateTaskViewHolder (
    private val binding: TaskViewBinding,
    private val onCheckClickListener: (id: Int) -> Unit,
    private val onDeleteClickListener: (id: Int) -> Unit,
    private val onTaskDetailClickListener: (id: Int) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(task: TaskEntity) {
        with(binding){
            tvTaskTitle.text = task.title
            cbIsCompleted.isChecked = task.isChecked

            cbIsCompleted.setOnClickListener{
                onCheckClickListener(task.id)
            }

            btnTrash.setOnClickListener {
                onDeleteClickListener(task.id)
            }

            root.setOnClickListener{
                onTaskDetailClickListener(task.id)
            }
        }
    }
}