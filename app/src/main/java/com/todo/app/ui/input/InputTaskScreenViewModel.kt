package com.todo.app.ui.input

import androidx.lifecycle.viewModelScope
import com.todo.app.base.BaseViewModel
import com.todo.app.data.repository.ToDoRepository
import com.todo.app.di.ToDoModule
import com.todo.app.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class InputTaskScreenViewModel @Inject constructor(
    @Named(ToDoModule.DISPATCHER_IO) private val dispatcherIO: CoroutineDispatcher,
    private val repository: ToDoRepository
) : BaseViewModel<InputTaskScreenViewModel.State, InputTaskScreenViewModel.Event>() {

    companion object {
        private const val TAG = "InputTaskScreenViewModel"
    }

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun saveToDoTask(taskModel: TaskModel) {
        viewModelScope.launch(dispatcherIO) {
            repository.insertTask(taskModel)
            _event.emit(Event.NavigateToHome)
        }
    }

    data class State(
        val taskModel: TaskModel
    )

    sealed class Event {
        class SaveToDoTask(val taskModel: TaskModel): Event()
        class SetDueDate(val dueDate: String): Event()
        object NavigateToHome : Event()
    }

    override fun defaultState(): State = State(TaskModel())

    override fun onEvent(event: Event) {
        viewModelScope.launch(dispatcherIO) {
            when(event) {
                is Event.SaveToDoTask -> _event.emit(Event.SaveToDoTask(event.taskModel))
                is Event.SetDueDate -> _event.emit(Event.SetDueDate(event.dueDate))
                is Event.NavigateToHome -> _event.emit(Event.NavigateToHome)
            }
        }
    }
}