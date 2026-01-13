package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.ae.watchme.R
import ru.ae.watchme.ui.viewmodels.MovieDetailsState
import ru.ae.watchme.ui.viewmodels.MovieDetailsViewModel
import ru.ae.watchme.ui.viewmodels.MovieListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    id: Int,
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = {
        parametersOf(id)
    })
) {

    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is MovieDetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailsState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = currentState.message)
            }
        }

        is MovieDetailsState.Success -> {
            val movie = currentState.movie

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(movie.name) },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = { viewModel.toggleFavourite() },
                        icon = {
                            Icon(
                                if (currentState.isFavourite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Filled.FavoriteBorder
                                },
                                contentDescription = null,
                                tint = if (currentState.isFavourite) {
                                    Color.Red
                                } else {
                                    Color.White
                                }
                            )
                        },
                        text = { Text(if (currentState.isFavourite) "В избранном" else "В избранное") }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.movie_poster_placeholder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = movie.name,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color.Yellow
                            )
                            Text(
                                text = "${movie.rating}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(text = "•", style = MaterialTheme.typography.bodyLarge)

                            Text(text = "${movie.year}", style = MaterialTheme.typography.bodyLarge)

                            Text(text = "•", style = MaterialTheme.typography.bodyLarge)

                            Text(
                                text = "${movie.ageRating}+",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                        Text(
                            text = "Описание",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = movie.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                        Text(
                            text = "Жанры",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            movie.genres.forEach {
                                SuggestionChip(
                                    label = { Text(it) },
                                    onClick = {
                                        /* Можно в будущем добавить переход на поиск по жанру,
                                        но пока что у нас нет ни времени ни ресурсов на это */
                                    }
                                )
                            }
                        }
                    }
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