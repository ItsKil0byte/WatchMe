package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieDetailsScreen(id: Int, onBackClick: () -> Unit) {
    Column() {
        Text("Автостопом по галактике. ID $id")
        Text("Лучше прочитайте книгу")
        Button(onClick = onBackClick) { Text("Вернуться назад") }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(id = 42, onBackClick = {})
}