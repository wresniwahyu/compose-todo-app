package com.todo.app.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInput(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Task Name")
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(id = R.string.description))
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(text = "Due Date")
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            readOnly = false,
            modifier = modifier
                .fillMaxWidth()
                .clickable {

                }
        )
        Spacer(modifier = modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TaskInputPreview() {
    TaskInput()
}