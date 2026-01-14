package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.ae.watchme.R
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.ui.viewmodels.MovieDetailsState
import ru.ae.watchme.ui.viewmodels.MovieDetailsViewModel
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieDetailsContent(
    state: MovieDetailsState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    when (state) {
        is MovieDetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailsState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }

        is MovieDetailsState.Success -> {
            val movie = state.movie

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
                        },
                        actions = {
                            IconButton(onClick = onFavoriteClick) {
                                Icon(
                                    if (state.isFavourite) {
                                        Icons.Filled.Favorite
                                    } else {
                                        Icons.Filled.FavoriteBorder
                                    },
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        }
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
                            .align(Alignment.CenterHorizontally)
                            .size(300.dp, 450.dp)
                            .clip(RoundedCornerShape(16.dp))
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
                                text = movie.rating,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(text = "•", style = MaterialTheme.typography.bodyLarge)

                            Text(text = movie.year, style = MaterialTheme.typography.bodyLarge)

                            Text(text = "•", style = MaterialTheme.typography.bodyLarge)

                            Text(
                                text = movie.ageRating,
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

                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            val genres = movie.genres.split(",").map {
                                it.trim().replaceFirstChar(Char::titlecase)
                            }

                            genres.forEach {
                                SuggestionChip(
                                    label = { Text(it) },
                                    onClick = { }
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
    val previewMovie = Movie(
        id = 6,
        _name = "Форрест Гамп",
        _description = "От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.",
        _shortDescription = "История жизни простого человека с большим сердцем",
        _year = 1994,
        _genres = listOf("Драма", "Комедия", "Мелодрама"),
        _posterUrl = "...",
        _previewUrl = "...",
        _rating = 8.8,
        _ageRating = 16
    )

    MovieDetailsContent(
        state = MovieDetailsState.Success(movie = previewMovie, isFavourite = false),
        onBackClick = {},
        onFavoriteClick = {}
    )
}

@Composable
fun MovieDetailsScreen(
    id: Int,
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = { parametersOf(id) })
) {
    val state by viewModel.state.collectAsState()

    MovieDetailsContent(
        state = state,
        onBackClick = onBackClick,
        onFavoriteClick = { viewModel.toggleFavourite() }
    )
}
