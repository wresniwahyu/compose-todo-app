package com.todo.app.provide

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigationController = staticCompositionLocalOf<NavHostController> { error("LocalNavigationController not implement") }