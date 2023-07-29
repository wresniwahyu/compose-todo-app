package com.todo.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoTask(todoEntity: TodoEntity)

    @Query("UPDATE TodoEntity SET title = :title, description = :description, due_date = :dueDate, is_checked = :isChecked WHERE id = :id")
    suspend fun updateToDoTask(id: String, title: String, description: String, dueDate: String, isChecked: Boolean)

    @Query("UPDATE TodoEntity SET is_checked = :isChecked WHERE id = :id")
    suspend fun updateChecked(id: String, isChecked: Boolean)

    @Query("DELETE FROM TodoEntity WHERE id = :id")
    suspend fun deleteToDoTaskById(id: String)

    @Query("SELECT * FROM TodoEntity")
    fun getAllToDoTask(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun getTaskById(id: String): Flow<TodoEntity>
}