package com.todo.app.ui.edit

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.app.R
import com.todo.app.component.TaskInput
import com.todo.app.model.TaskModel
import com.todo.app.provide.LocalNavigationController
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val navigation = LocalNavigationController.current

    val context = LocalContext.current
    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val eventLaunch by rememberUpdatedState(viewModel::onEvent)
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dueDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, year, month, day
    )

    LaunchedEffect(Unit) {
        viewModel.setID(id)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is EditTaskViewModel.Event.SaveToDoTask -> {
                    viewModel.updateToDoTask(event.taskModel)
                }

                is EditTaskViewModel.Event.SetTaskModel -> {
                    event.taskModel.run {
                        title = this.title
                        description = this.description
                        dueDate = this.dueDate
                    }
                }

                is EditTaskViewModel.Event.NavigateToHome -> {
                    navigation.navigate("home") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_task_edit),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigation.navigate("home") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
            TaskInput(
                title = title,
                onTitleChange = { title = it },
                description = description,
                onDescChange = { description = it },
                onDateClick = {
                    datePicker.show()
                },
                dueDate = dueDate
            )
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    ),
                onClick = {
                    eventLaunch.invoke(
                        EditTaskViewModel.Event.SaveToDoTask(
                            TaskModel(
                                id = id,
                                title = title,
                                description = description,
                                dueDate = dueDate
                            )
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.button_save))
            }
        }
    }
}