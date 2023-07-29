package com.todo.app.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.todo.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInput(
    modifier: Modifier = Modifier,
    title: String = "",
    onTitleChange: (String) -> Unit = {},
    description: String = "",
    onDescChange: (String) -> Unit = {},
    onDateClick: () -> Unit = {},
    dueDate: String = ""
) {
    val titleChange by rememberUpdatedState(onTitleChange)
    val descChange by rememberUpdatedState(onDescChange)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = titleChange,
            label = {
                Text(text = stringResource(R.string.task_name))
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )
        Spacer(modifier = modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = descChange,
            label = {
                Text(text = stringResource(R.string.description))
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )
        Spacer(modifier = modifier.height(8.dp))

        Row(
            modifier = modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onDateClick.invoke()
                },
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
                text = dueDate.ifEmpty {
                    stringResource(id = R.string.add_due_date)
                }
            )
        }
        Spacer(modifier = modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TaskInputPreview() {
    TaskInput()
}