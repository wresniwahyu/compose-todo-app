package com.todo.app.model

data class TaskModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val dueDate: String = "",
    val isChecked: Boolean = false,
)
