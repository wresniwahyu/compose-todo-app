package com.todo.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoTask(todoEntity: TodoEntity)

    @Query("UPDATE TodoEntity SET title = :title, description = :description, due_date = :dueDate WHERE id = :id")
    suspend fun updateToDoTask(id: String, title: String, description: String, dueDate: String?)

    @Query("DELETE FROM TodoEntity WHERE id = :id")
    suspend fun deleteToDoTaskById(id: String)

    @Query("SELECT * FROM TodoEntity")
    fun getAllToDoTask(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun getTaskById(id: String): Flow<TodoEntity>
}