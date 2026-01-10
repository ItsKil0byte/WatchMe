package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RandomWheelScreen(onBackClick: () -> Unit) {
    Column() {
        Text("Колесо рандома (WIP)")
        Button(onClick = onBackClick) { Text("Вернуться назад") }
    }
}

@Preview
@Composable
fun RandomWheelScreenPreview() {
    RandomWheelScreen(onBackClick = {})
}