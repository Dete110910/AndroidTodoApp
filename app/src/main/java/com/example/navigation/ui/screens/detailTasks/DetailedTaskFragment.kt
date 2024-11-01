package com.example.navigation.ui.screens.detailTasks

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigation.data.entities.TaskEntity
import com.example.navigation.data.viewModel.TaskViewModel
import com.example.navigation.databinding.FragmentDetailTaskBinding


class DetailedTaskFragment : Fragment() {

    private lateinit var binding: FragmentDetailTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val args: DetailedTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTaskBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        setListeners()
        taskViewModel.findTaskById(args.taskId)?.let { task ->
            with(binding) {
                tvTaskTitle.setText(task.title)
                cbIsCompleted.isChecked = task.isChecked
                etDescriptionTask.setText(task.description)

                if (task.isChecked) {
                    tvTaskTitle.paintFlags =
                        tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        } ?: {
            Toast.makeText(context, "Tarea no encontrada", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

    }

    private fun setListeners() {
        with(binding){
            btnBack.setOnClickListener {
                if (tvTaskTitle.text.toString().trim() != "")
                    findNavController().popBackStack()
                else
                    Toast.makeText(context, "No puede dejar el título vacío", Toast.LENGTH_SHORT)
                        .show()
            }

            cbIsCompleted.setOnClickListener {
                if (cbIsCompleted.isChecked)
                    tvTaskTitle.paintFlags =
                        tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else
                    tvTaskTitle.paintFlags =
                        tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            btnTrash.setOnClickListener {
                taskViewModel.findTaskById(args.taskId)?.let {
                    taskViewModel.deleteTask(it)
                    findNavController().popBackStack()
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            taskViewModel.taskList.find { it.id == args.taskId }
                ?.let { task ->
                    if (tvTaskTitle.text.toString().trim() != "") {
                        task.title = tvTaskTitle.text.toString()
                        task.description = etDescriptionTask.text.toString()
                        task.isChecked = cbIsCompleted.isChecked
                        taskViewModel.updateTask(task)
                        taskViewModel.updateTaskList()
                    } else {
                        Toast.makeText(
                            context,
                            "No puede dejar el título vacío",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }
}