package com.example.navigation.data.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.models.Task
import com.example.navigation.ui.screens.createTasks.uiState.TaskUiState
import com.example.navigation.ui.screens.detailTasks.uiState.DetailTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _uiStateDetail = MutableStateFlow(DetailTaskUiState())
    val uiStateDetail: StateFlow<DetailTaskUiState> = _uiStateDetail.asStateFlow()

    private lateinit var selectedTask: Task

    var taskList = mutableListOf(
        Task(1, "T1", "DT1", false),
        Task(2, "T2", "DT2", false),
        Task(3, "T3", "DT3", false),
    )

    fun updateUiState() {
        viewModelScope.launch {
            val newUiState = _uiState.value.copy(
                taskList = taskList
            )
            _uiState.value = newUiState
        }
    }

    fun setSelectedTask(idTask: Int) {
        val task = taskList.find { it.id == idTask }
        if (task != null)
            this.selectedTask = task
    }

    fun getSelectedTask(): Task {
        return this.selectedTask
    }

    fun addTask(title: String) {
        taskList.add(Task(taskList.size + 1, title, "", false))
        updateUiState()
    }
}