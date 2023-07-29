package com.todo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.todo.app.provide.LocalNavigationController
import com.todo.app.ui.detail.TaskDetailScreen
import com.todo.app.ui.input.InputTaskScreen
import com.todo.app.ui.list.TasksScreen
import com.todo.app.ui.splash.SplashScreen
import com.todo.app.ui.theme.ToDoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var splash by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                splash = true
                delay(2000)
                splash = false
            }

            ToDoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navigation = rememberNavController()

                    CompositionLocalProvider(LocalNavigationController provides navigation) {
                        if (splash) {
                            SplashScreen()
                        } else {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navigation = LocalNavigationController.current

    NavHost(navController = navigation, startDestination = "home") {
        composable(route = "splash") { SplashScreen() }
        composable(route = "home") { TasksScreen() }
        composable(route = "task_input") { InputTaskScreen() }
        composable(
            route = "task_detail?id={id}",
            arguments = listOf(navArgument("id"){ defaultValue = ""})
        ) {
            val id = it.arguments?.getString("id") ?: ""
            if (id.isBlank()) {
                navigation.navigateUp()
                return@composable
            }

            TaskDetailScreen(id = id)
        }
    }
}