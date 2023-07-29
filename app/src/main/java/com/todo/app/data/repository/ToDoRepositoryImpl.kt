package com.todo.app.data.repository

import com.todo.app.data.local.ToDoDao
import com.todo.app.di.ToDoModule.DISPATCHER_IO
import com.todo.app.mapper.toEntity
import com.todo.app.mapper.toModels
import com.todo.app.mapper.toUiModel
import com.todo.app.model.TaskModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ToDoRepositoryImpl @Inject constructor(
    @Named(DISPATCHER_IO)
    private val dispatcherIO: CoroutineDispatcher,
    private val dao: ToDoDao
): ToDoRepository {
    override suspend fun insertTask(taskModel: TaskModel) {
        withContext(dispatcherIO) {
            dao.insertToDoTask(taskModel.toEntity())
        }
    }

    override suspend fun updateTaskById(taskModel: TaskModel) {
        withContext(dispatcherIO) {
            dao.updateToDoTask(
                id = taskModel.id,
                title = taskModel.title,
                description = taskModel.description,
                dueDate = taskModel.dueDate
            )
        }
    }

    override suspend fun deleteTaskById(id: String) {
        withContext(dispatcherIO) {
            dao.deleteToDoTaskById(id)
        }
    }

    override fun getAllToDoTask(): Flow<List<TaskModel>> {
        return dao.getAllToDoTask()
            .map { it.toModels() }
            .flowOn(dispatcherIO)
    }

    override fun getTodoTaskById(id: String): Flow<TaskModel> {
        return dao.getTaskById(id)
            .filterNotNull()
            .map { it.toUiModel() }
            .flowOn(dispatcherIO)
    }
}