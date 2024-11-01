package com.example.navigation.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.db.TaskDao
import com.example.navigation.data.entities.TaskEntity
import com.example.navigation.ui.uiState.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()
    var taskList = mutableListOf<TaskEntity>()

    init {
        updateTaskList()
    }
    fun findTaskById(idTask: Int) = taskList.find { it.id == idTask }

    fun updateTaskList() {
        viewModelScope.launch {
            val tasks = taskDao.getAllTasks()
            taskList = tasks.toMutableList()
            _uiState.update { it.copy(taskList = tasks) }
        }
    }

    fun addTask(title: String) {
        viewModelScope.launch {
            taskDao.insertTask(TaskEntity(title = title, description = "", isChecked = false))
            updateTaskList()
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.updateTask(task)
            updateTaskList()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
            updateTaskList()
        }
    }
}