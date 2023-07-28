package com.todo.app.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todo.app.model.TaskModel

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    taskModel: TaskModel = TaskModel(),
    onClick: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = true, onCheckedChange = onClick)
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = taskModel.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
            if (taskModel.description.isNotEmpty()) {
                Text(
                    text = taskModel.description,
                    style = TextStyle(
                        fontSize = 10.sp
                    ),
                )
            }
            if (taskModel.dueDate.isNotEmpty()) {
                Text(
                    text = taskModel.dueDate,
                    style = TextStyle(
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem()
}