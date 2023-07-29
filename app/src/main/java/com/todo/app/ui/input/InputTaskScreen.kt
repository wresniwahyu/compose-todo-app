package com.todo.app.ui.input

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todo.app.R
import com.todo.app.component.TaskInput
import com.todo.app.provide.LocalNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTaskScreen(
    modifier: Modifier = Modifier
) {
    val navigation = LocalNavigationController.current
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_task_input),
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
            TaskInput()
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    ),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(R.string.button_save))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputTaskScreenPreview() {
    InputTaskScreen()
}