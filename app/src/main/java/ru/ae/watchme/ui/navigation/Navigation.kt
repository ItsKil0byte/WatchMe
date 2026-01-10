package ru.ae.watchme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ru.ae.watchme.ui.screens.MovieDetailsScreen
import ru.ae.watchme.ui.screens.MovieListScreen
import ru.ae.watchme.ui.screens.RandomWheelScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MovieList
    ) {
        composable<Screen.MovieList> {
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
            RandomWheelScreen(onBackClick = { navController.popBackStack() })
        }

        composable<Screen.MovieDetails> { backStackEntity ->
            val id: Int = backStackEntity.toRoute<Screen.MovieDetails>().id
            MovieDetailsScreen(id = id, onBackClick = { navController.popBackStack() })
        }
    }
}