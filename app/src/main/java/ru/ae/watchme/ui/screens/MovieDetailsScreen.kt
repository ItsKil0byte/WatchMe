package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.ae.watchme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(id: Int, onBackClick: () -> Unit) {

    // Фейковый фильм
    val movieName = "Фильм $id"
    val description =
        "Очень крутой фильм про бандитский Петербург, там есть тачки, пушки, мужики в форме и... Погодите, ты зачем это читаешь?"
    val year = "2005"
    val genres = listOf("Боевик", "Детектив")
    val poster = "https://placecats.com/300/200"
    val rating = "9.2"
    val ageRating = "16+"

    // TODO: Перенести во view model
    var isFavourite by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("О Фильме") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Вернуться назад"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isFavourite = !isFavourite },
                icon = {
                    Icon(
                        if (isFavourite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription = "Избранное",
                        tint = if (isFavourite) {
                            Color.Red
                        } else {
                            Color.White
                        }
                    )
                },
                text = { Text(if (isFavourite) "В избранном" else "Буду смотреть") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AsyncImage(
                model = poster,
                contentDescription = "Постер фильма",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.movie_poster_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = movieName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Text(
                        text = rating, style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Год: $year", style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "($ageRating)", style = MaterialTheme.typography.bodyLarge
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Описание:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Жанры:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TODO: Придумать отображение жанров
                genres.forEach { text ->
                    Text(text)
                }

            }
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(id = 42, onBackClick = {})
}