package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ae.watchme.ui.components.MovieCard
import ru.ae.watchme.ui.components.MovieModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    onRandomWheelClick: () -> Unit
) {

    // Список заглушка
    val movies = List(10) { i ->
        MovieModel(
            id = i,
            title = "Фильм",
            image = "https://placecats.com/125/175",
            rating = i.toDouble()
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watch Me!") },
                actions = {
                    IconButton(onClick = onRandomWheelClick) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Открыть колесо рандома"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(movies) {
                MovieCard(movieModel = it)
            }
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    MovieListScreen(onMovieClick = {}, onRandomWheelClick = {})
}