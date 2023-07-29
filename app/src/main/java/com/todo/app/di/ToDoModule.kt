package com.todo.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {

    const val DISPATCHER_IO = "Dispatcher.IO"
    const val DISPATCHER_MAIN = "Dispatcher.MAIN"

    @Provides
    @Named(DISPATCHER_IO)
    fun provideDispatcherIo(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Named(DISPATCHER_MAIN)
    fun provideDispatcherMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

}