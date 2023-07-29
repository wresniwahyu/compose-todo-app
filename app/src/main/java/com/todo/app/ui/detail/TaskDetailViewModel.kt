package com.todo.app.ui.detail

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
class TaskDetailViewModel @Inject constructor(
    @Named(DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    private val repository: ToDoRepository
) : BaseViewModel<TaskDetailViewModel.State, TaskDetailViewModel.Event>() {

    companion object {
        const val TAG = "TaskDetailViewModel"
    }

    private val _event = MutableSharedFlow<TaskDetailViewModel.Event>()
    val event = _event.asSharedFlow()

    fun setID(id: String) {
        getToDoTaskById(id)
    }

    private fun getToDoTaskById(id: String) {
        viewModelScope.launch(dispatcherIO) {
            repository.getTodoTaskById(id)
                .catch { Log.e(TAG, "getTodoTaskById: failed", it) }
                .collectLatest {
                    _state.update { state ->
                        state.copy(taskModel = it)
                    }
                }
        }
    }

    fun deleteTaskById(id: String) {
        viewModelScope.launch(dispatcherIO) {
            repository.deleteTaskById(id)
            _event.emit(Event.NavigateToHome)
        }
    }

    fun editTask(taskModel: TaskModel) {
        viewModelScope.launch {
            _event.emit(Event.NavigateToEdit(taskModel))
        }
    }

    fun updateChecked(isChecked: Boolean) {
        viewModelScope.launch {
            val task = state.value.taskModel.copy(isChecked = isChecked)
            repository.updateCheckedById(task)
        }
    }

    data class State(
        val taskModel: TaskModel
    )

    sealed class Event {
        class NavigateToEdit(val taskModel: TaskModel) : Event()
        object NavigateToHome : Event()
    }

    override fun defaultState(): State = State(TaskModel())

    override fun onEvent(event: Event) {
        viewModelScope.launch(dispatcherIO) {
            when (event) {
                is Event.NavigateToEdit -> {
                    _event.emit(Event.NavigateToEdit(event.taskModel))
                }

                is Event.NavigateToHome -> {
                    _event.emit(Event.NavigateToHome)
                }
            }
        }
    }
}