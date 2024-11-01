package com.example.navigation.ui.screens.createTasks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.R
import com.example.navigation.data.viewModel.TaskViewModel
import com.example.navigation.databinding.FragmentCreateTaskBinding
import com.example.navigation.ui.rv.CreateTaskAdapter
import kotlinx.coroutines.launch

class CreateTaskFragment : Fragment() {

    private lateinit var binding: FragmentCreateTaskBinding
    private lateinit var rvTaskAdapter: CreateTaskAdapter
    private val taskViewModel: TaskViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        initRv()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateUiTaskList()
    }

    override fun onPause() {
        super.onPause()
        cleanField()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCompletedTasks.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        binding.ctvTitleTask.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.ctvTitleTask.text.toString().trim() != "") {
                    taskViewModel.addTask(binding.ctvTitleTask.text.toString())
                    cleanField()
                    updateUiTaskList()
                    true
                } else {
                    Toast.makeText(context, "No puede dejar el título vacío", Toast.LENGTH_SHORT)
                        .show()
                    false
                }
            } else {
                false
            }
        }
    }

    private fun initRv() {
        rvTaskAdapter = CreateTaskAdapter(
            taskViewModel.taskList,
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
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUiTaskList() {
        lifecycleScope.launch {
            taskViewModel.uiState.collect { uiState ->
                if (uiState.taskList.isNotEmpty()) {
                    rvTaskAdapter.updateTaskList(uiState.getPending())
                    rvTaskAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun checkTask(id: Int) {
        taskViewModel.taskList.find { it.id == id }?.let { task ->
            task.isChecked = true
            taskViewModel.updateTask(task)
            updateUiTaskList()
        }
    }

    private fun deleteTask(id: Int) {
        taskViewModel.findTaskById(id)?.let {
            taskViewModel.deleteTask(it)
            updateUiTaskList()
        }
    }

    private fun launchFragmentDetail(idTask: Int) {
        val actions = CreateTaskFragmentDirections.actionFirstFragmentToThirdFragment(
            taskId = idTask,
            taskTitle = ""
        )
        findNavController().navigate(actions)
    }


    private fun cleanField() {
        binding.ctvTitleTask.setText("")
    }
}