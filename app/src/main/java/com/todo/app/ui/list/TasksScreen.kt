package com.todo.app.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todo.app.R
import com.todo.app.component.TaskItem
import com.todo.app.model.TaskModel
import com.todo.app.provide.LocalNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier
) {

    val navigation = LocalNavigationController.current
    // TODO(www): remove this dummy data when actual local db ready
    val list = listOf(
        TaskModel("1", "Cuci Mobil", "Cuci mobil pagi hari", "08 08 2023"),
        TaskModel("2", "Cuci Motor"),
        TaskModel("3", "Buang Sampah", "Buang sampah makanan dan sampah biasa", "15 08 2023"),
        TaskModel("4", "Cek Lampu", "Cek lampu dan elektronik lainnya"),
    )

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
            LazyColumn(
                modifier = modifier
                    .weight(1f),
            ) {
                this.items(
                    count = list.size
                ) {
                    TaskItem(
                        taskModel = list[it],
                        onClick = {
                            navigation.navigate("task_detail")
                        }
                    )
                }
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