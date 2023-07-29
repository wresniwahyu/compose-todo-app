package com.todo.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.DelicateCoroutinesApi

@Database(
    entities = [TodoEntity::class],
    version = 1
)

abstract class ToDoDb : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    @DelicateCoroutinesApi
    companion object {
        private const val DB_NAME = "todo-db"

        @Volatile
        private var INSTANCE: ToDoDb? = null

        fun getInstance(context: Context): ToDoDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ToDoDb {
            val db = Room.databaseBuilder(
                context,
                ToDoDb::class.java,
                DB_NAME
            )

            return db.build()
        }

    }

}