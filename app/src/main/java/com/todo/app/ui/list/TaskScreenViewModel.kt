package com.todo.app.ui.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.todo.app.base.BaseViewModel
import com.todo.app.data.repository.ToDoRepository
import com.todo.app.di.ToDoModule.DISPATCHER_IO
import com.todo.app.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    @Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    private val repository: ToDoRepository
): BaseViewModel<TaskScreenViewModel.State, TaskScreenViewModel.Event>() {

    companion object {
        private const val TAG = "TaskScreenViewModel"
    }

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch(dispatcherIO) {
            repository.getAllToDoTask()
                .catch { Log.e(TAG, "getAllToDoTask: failed", it) }
                .collectLatest {
                    _state.update { state ->
                        state.copy(listTask = it)
                    }
                }
        }
    }

    data class State(
        val listTask: List<TaskModel>
    )

    sealed class Event {
        class SetData(val taskModel: TaskModel): Event()
        class MoveToInput(val taskModel: TaskModel) : Event()
    }

    override fun defaultState(): State = State(
        listTask = listOf()
    )

    override fun onEvent(event: Event) {
        viewModelScope.launch(dispatcherIO) {
            when(event) {
                is Event.SetData -> {
                    _event.emit(Event.SetData(event.taskModel))
                }
                is Event.MoveToInput -> {
                    _event.emit(Event.MoveToInput(event.taskModel))
                }
            }
        }
    }
}