package com.example.navigation.ui.screens.createTasks

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
import com.example.navigation.data.models.Task
import com.example.navigation.data.viewModel.TaskViewModel
import com.example.navigation.databinding.FragmentCreateTaskBinding
import com.example.navigation.ui.screens.createTasks.rv.CreateTaskAdapter
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
        initUiStateLifecycle()
        return binding.root
    }

    private fun initRv() {
        rvTaskAdapter = CreateTaskAdapter(
            taskViewModel.taskList,
            onCheckClickListener = { idTask ->
                checkTask(idTask)
            }
        )
        Log.d("TEST--", "Fragment")
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvTaskAdapter
        }
    }

    private fun initUiStateLifecycle() {
        Log.d("TEST", "Falta implementar")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkTask(id: Int) {
        taskViewModel.taskList.find { it.id == id }!!.isChecked = true
        taskViewModel.setPendingTasks(taskList = taskViewModel.taskList)
        lifecycleScope.launch {
            taskViewModel.uiState.collect{ uiState ->
                if(uiState.taskList.isNotEmpty()){
                    rvTaskAdapter.taskList = uiState.getPending()
                    rvTaskAdapter.notifyDataSetChanged()
                }
            }
        }
        //Log.d("TEST", "Aquí: ${taskList}")
        //Log.d("TEST", "Aquí 2: ${a}")

        //Cuando entre aquí modifico modifico el viewmodel uistate adapter para pasarle la lista con los completes.
        //Cuando haga el otro rv, le paso los complete nomás
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCompletedTasks.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }
}