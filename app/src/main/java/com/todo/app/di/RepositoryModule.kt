package com.todo.app.di

import com.todo.app.data.repository.ToDoRepository
import com.todo.app.data.repository.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTodoRepository(
        impl: ToDoRepositoryImpl
    ): ToDoRepository

}