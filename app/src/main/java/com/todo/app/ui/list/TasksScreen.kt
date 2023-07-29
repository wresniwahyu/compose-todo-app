package com.todo.app.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.app.R
import com.todo.app.component.EmptyState
import com.todo.app.component.TaskItem
import com.todo.app.provide.LocalNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navigation = LocalNavigationController.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is TaskScreenViewModel.Event.MoveToInput -> {}
                is TaskScreenViewModel.Event.SetData -> {}
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.title_list_of_tasks),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            })

            if (state.listTask.isNotEmpty()) {
                LazyColumn(modifier = modifier.weight(1f)) {
                    items(state.listTask) {
                        key(it.id) {
                            TaskItem(
                                taskModel = it,
                                onClick = {
                                    navigation.navigate("task_detail?id=${it.id}")
                                },
                                onChecked = {

                                }
                            )
                        }
                    }

                }
            } else {
                EmptyState(modifier = modifier.weight(1f))
            }

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    navigation.navigate("task_input")
                }
            ) {
                Text(text = stringResource(R.string.button_add_new_task))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    TasksScreen()
}