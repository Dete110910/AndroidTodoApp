package com.example.navigation.data.models

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    var isChecked: Boolean
)