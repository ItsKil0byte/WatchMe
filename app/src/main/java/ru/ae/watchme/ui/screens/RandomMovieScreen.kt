package ru.ae.watchme.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.ae.watchme.R
import ru.ae.watchme.ui.components.BigMovieCard
import ru.ae.watchme.ui.viewmodels.RandomMovieState
import ru.ae.watchme.ui.viewmodels.RandomMovieViewModel


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RandomMovieContent(
    state: RandomMovieState,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onShuffleClick: () -> Unit
) {
    val transition = rememberInfiniteTransition()
    val offset by transition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Что позырим?") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is RandomMovieState.Empty -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painterResource(R.drawable.heart_broken_24),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "В избранном пусто :(",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Лайкайте фильмы, чтобы рандом заработал!",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Фейковые карточки
                        repeat(5) { index ->
                            Card(
                                modifier = Modifier
                                    .size(250.dp, 390.dp)
                                    .graphicsLayer {
                                        rotationZ = (index - 0.5f) * 4f
                                        translationX =
                                            if (state is RandomMovieState.Shuffling) offset * (index + 1) else 0f
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = (index * 2).dp)
                            ) {}
                        }

                        AnimatedContent(
                            targetState = state,
                            transitionSpec = {
                                (fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.8f))
                                    .togetherWith(fadeOut(animationSpec = tween(200)) + scaleOut())
                            }
                        ) { currentState ->
                            when (currentState) {
                                is RandomMovieState.Idle, is RandomMovieState.Shuffling -> {
                                    Card(
                                        modifier = Modifier.size(240.dp, 380.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Box(
                                            Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    Icons.Default.PlayArrow,
                                                    null,
                                                    Modifier.size(100.dp),
                                                    tint = Color.White
                                                )
                                                Spacer(Modifier.height(16.dp))
                                                Text(
                                                    text = if (currentState is RandomMovieState.Shuffling) "Выбираем..." else "Удиви меня!",
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                            }
                                        }
                                    }
                                }

                                is RandomMovieState.Success -> {
                                    BigMovieCard(
                                        movie = currentState.movie,
                                        onClick = { onMovieClick(currentState.movie.id) }
                                    )
                                }

                                else -> {
                                    /* Да не  может быть такого */
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(52.dp))

                    Button(
                        onClick = onShuffleClick,
                        enabled = state !is RandomMovieState.Shuffling,
                        modifier = Modifier
                            .height(52.dp)
                            .fillMaxWidth(0.5f)
                    ) {
                        if (state is RandomMovieState.Shuffling) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Удиви меня!")
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RandomMovieScreenPreview() {
    RandomMovieContent(
        state = RandomMovieState.Idle,
        onBackClick = {},
        onMovieClick = {},
        onShuffleClick = {}
    )
}

@Composable
fun RandomMovieScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: RandomMovieViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    RandomMovieContent(
        state = state,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick,
        onShuffleClick = { viewModel.pick() }
    )
}