package com.example.navigation.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.models.Task
import com.example.navigation.ui.screens.createTasks.uiState.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    var taskList = listOf(
        Task(1, "T1", "DT1", false),
        Task(2, "T2", "DT2", false),
        Task(3, "T3", "DT3", false),
    )

    fun setPendingTasks(taskList: List<Task>){
        viewModelScope.launch {
            val newUiState = _uiState.value.copy(
                taskList = taskList
            )
            _uiState.value = newUiState
        }
    }
}