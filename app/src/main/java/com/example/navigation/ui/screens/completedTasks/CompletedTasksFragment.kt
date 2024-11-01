package com.example.navigation.ui.screens.completedTasks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.data.viewModel.TaskViewModel
import com.example.navigation.databinding.FragmentCompletedTaskBinding
import com.example.navigation.ui.rv.CreateTaskAdapter
import kotlinx.coroutines.launch


class CompletedTasksFragment : Fragment() {

    private lateinit var binding: FragmentCompletedTaskBinding
    private lateinit var rvTaskAdapter: CreateTaskAdapter
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedTaskBinding.inflate(layoutInflater)
        initRv()
        return binding.root
    }

    private fun initRv() {
        rvTaskAdapter = CreateTaskAdapter(
            taskViewModel.uiState.value.getCompleted(),
            onCheckClickListener = { idTask ->
                checkTask(idTask)
            },
            onDeleteClickListener = { idTask ->
                deleteTask(idTask)
            },
            onTaskDetailClickListener = { idTask ->
                launchFragmentDetail(idTask)
            }
        )
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvTaskAdapter
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUiTaskListCompleted() {
        lifecycleScope.launch {
            taskViewModel.uiState.collect { uiState ->
                if (uiState.taskList.isNotEmpty()) {
                    rvTaskAdapter.updateTaskList(uiState.getCompleted())
                    rvTaskAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkTask(id: Int) {
        val task = taskViewModel.taskList.find { it.id == id }
        if (task != null) {
            task.isChecked = false
            taskViewModel.updateTask(task)
        }
        updateUiTaskListCompleted()
    }

    private fun deleteTask(id: Int) {
        taskViewModel.findTaskById(id)?.let {
            taskViewModel.deleteTask(it)
            updateUiTaskListCompleted()
        }
    }

    private fun launchFragmentDetail(idTask: Int) {
        val actions = CompletedTasksFragmentDirections.actionSecondFragmentToThirdFragment(
            taskId = idTask,
            taskTitle = ""
        )
        findNavController().navigate(actions)
    }

    override fun onResume() {
        super.onResume()
        updateUiTaskListCompleted()
    }
}