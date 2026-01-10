package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    onRandomWheelClick: () -> Unit
) {
    Column() {
        Text("Список фильмов (WIP)")
        Button(onClick = { onMovieClick(42) }) { Text("Открыть фильм 42") }
        Button(onRandomWheelClick) { Text("Перейти к колесу рандома") }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    MovieListScreen(onMovieClick = {}, onRandomWheelClick = {})
}