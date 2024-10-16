package com.example.navigation.ui.screens.createTasks.uiState

import com.example.navigation.data.models.Task

data class TaskUiState(
    val taskList: List<Task> = emptyList()
) {
    fun getPending() = taskList.filter { it.isChecked.not() }
    fun getCompleted() = taskList.filter { it.isChecked }
}