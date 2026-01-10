package ru.ae.watchme.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.ae.watchme.ui.screens.MovieListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MovieList
    ) {
        composable<Screen.MovieList> {
            // Text("It's works!!!")

            MovieListScreen(
                onMovieClick = { id ->
                    navController.navigate(Screen.MovieDetails(id))
                },
                onRandomWheelClick = {
                    navController.navigate(Screen.RandomWheel)
                }
            )
        }

        composable<Screen.RandomWheel> {
            // TODO
            Text("Колесо рандома (WIP)")
        }

        composable<Screen.MovieDetails> {
            // TODO
            Text("Автостопом по галактике (WIP)")
        }
    }
}