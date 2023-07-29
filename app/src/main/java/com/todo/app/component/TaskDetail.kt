package com.todo.app.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.todo.app.R
import com.todo.app.model.TaskModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetail(
    modifier: Modifier = Modifier,
    taskModel: TaskModel = TaskModel()
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {})
            Text(
                modifier = modifier.fillMaxWidth(),
                text = taskModel.title
            )
        }

        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = taskModel.description,
            label = {
                Text(text = stringResource(R.string.description))
            },
            readOnly = true,
            onValueChange = {},
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = R.drawable.ic_calendar,
                contentDescription = null,
                modifier = modifier
                    .width(24.dp)
                    .height(24.dp)
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = taskModel.dueDate.ifEmpty {
                    stringResource(R.string.add_due_date)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailPreview() {
    TaskDetail()
}