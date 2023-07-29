package com.todo.app.data.repository

import com.todo.app.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun insertTask(taskModel: TaskModel)
    suspend fun updateTaskById(taskModel: TaskModel)
    suspend fun updateCheckedById(taskModel: TaskModel)
    suspend fun deleteTaskById(id: String)
    fun getAllToDoTask(): Flow<List<TaskModel>>
    fun getTodoTaskById(id: String): Flow<TaskModel>
}