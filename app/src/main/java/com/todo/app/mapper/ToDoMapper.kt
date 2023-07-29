package com.todo.app.mapper

import com.todo.app.data.local.TodoEntity
import com.todo.app.model.TaskModel

fun List<TodoEntity>.toModels(): List<TaskModel> {
    return this.map { it.toUiModel() }
}

fun List<TaskModel>.toEntities(): List<TodoEntity> {
    return this.map { it.toEntity() }
}

fun TodoEntity.toUiModel(): TaskModel {
    return TaskModel(
        id = this.id,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate.orEmpty()
    )
}

fun TaskModel.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate
    )
}