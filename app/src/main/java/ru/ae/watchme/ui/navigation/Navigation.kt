package ru.ae.watchme.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MovieList
    ) {
        composable<Screen.MovieList> {
            // TODO
            Text("It's works!!!")
        }

        composable<Screen.RandomWheel> {
            // TODO
        }

        composable<Screen.MovieDetails> {
            // TODO
        }
    }
}