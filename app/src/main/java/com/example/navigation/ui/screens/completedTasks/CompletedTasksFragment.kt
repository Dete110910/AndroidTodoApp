package com.example.navigation.ui.screens.completedTasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.R
import com.example.navigation.data.viewModel.TaskViewModel
import com.example.navigation.databinding.FragmentCompletedTaskBinding
import com.example.navigation.ui.screens.createTasks.rv.CreateTaskAdapter
import kotlinx.coroutines.launch


class CompletedTasksFragment : Fragment() {

    private lateinit var binding: FragmentCompletedTaskBinding
    private lateinit var rvTaskAdapter: CreateTaskAdapter
    private val taskViewModel : TaskViewModel by activityViewModels()

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
            onTaskDetailClickListener = { idTask ->
                launchActivityDetail(idTask)
            }
        )
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvTaskAdapter
        }

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUiState(){
        lifecycleScope.launch {
            taskViewModel.uiState.collect{ uiState ->
                if(uiState.taskList.isNotEmpty()){
                    rvTaskAdapter.updateTaskList(uiState.getCompleted())
                    rvTaskAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkTask(id: Int) {
        taskViewModel.taskList.find { it.id == id }!!.isChecked = false
        updateUiState()
    }

    private fun launchActivityDetail(idTask: Int) {
        taskViewModel.setSelectedTask(idTask)
        findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
    }
}