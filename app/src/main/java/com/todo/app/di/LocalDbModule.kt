package com.todo.app.di

import android.content.Context
import com.todo.app.data.local.ToDoDao
import com.todo.app.data.local.ToDoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {

    @Singleton
    @Provides
    fun provideToDoDao(
        @ApplicationContext context: Context
    ): ToDoDao {
        return ToDoDb.getInstance(context).toDoDao()
    }

}