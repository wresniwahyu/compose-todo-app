package com.todo.app.ui.edit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.todo.app.base.BaseViewModel
import com.todo.app.data.repository.ToDoRepository
import com.todo.app.di.ToDoModule
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
class EditTaskViewModel @Inject constructor(
    @Named(ToDoModule.DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    private val repository: ToDoRepository
) : BaseViewModel<EditTaskViewModel.State, EditTaskViewModel.Event>() {

    companion object {
        private const val TAG = "EditTaskViewModel"
    }

    private val _event = MutableSharedFlow<Event>()
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
                    _event.emit(Event.SetTaskModel(_state.value.taskModel))
                }
        }
    }

    fun updateToDoTask(taskModel: TaskModel) {
        viewModelScope.launch(dispatcherIO) {
            val task = state.value.taskModel.copy(
                title = taskModel.title,
                description = taskModel.description,
                dueDate = taskModel.dueDate
            )
            repository.updateTaskById(task)
            _event.emit(Event.NavigateToHome)
        }
    }

    data class State(
        val taskModel: TaskModel
    )

    sealed class Event {
        class SaveToDoTask(val taskModel: TaskModel) : Event()
        class SetTaskModel(val taskModel: TaskModel) : Event()
        object NavigateToHome : Event()
    }

    override fun defaultState(): State = State(TaskModel())

    override fun onEvent(event: Event) {
        viewModelScope.launch(dispatcherIO) {
            when (event) {
                is Event.SaveToDoTask -> _event.emit(Event.SaveToDoTask(event.taskModel))
                is Event.SetTaskModel -> _event.emit(Event.SetTaskModel(event.taskModel))
                is Event.NavigateToHome -> _event.emit(Event.NavigateToHome)
            }
        }
    }
}