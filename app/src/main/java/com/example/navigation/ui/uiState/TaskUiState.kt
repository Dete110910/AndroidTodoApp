package com.example.navigation.ui.uiState

import com.example.navigation.data.entities.TaskEntity

data class TaskUiState(
    val taskList: List<TaskEntity> = emptyList()
) {
    fun getPending() = taskList.filter { it.isChecked.not() }
    fun getCompleted() = taskList.filter { it.isChecked }
}