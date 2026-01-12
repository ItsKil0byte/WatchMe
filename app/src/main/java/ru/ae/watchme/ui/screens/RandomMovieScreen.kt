package ru.ae.watchme.ui.screens

import android.os.Handler
import android.os.Looper
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ae.watchme.ui.components.BigMovieCard
import ru.ae.watchme.ui.components.MovieModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomMovieScreen(onMovieClick: (Int) -> (Unit), onBackClick: () -> Unit) {

    // Анимация вышла посредственной, но пока что так.

    var isShuffling by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<MovieModel?>(null) }

    val transition = rememberInfiniteTransition()
    val offset by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (isShuffling) 15f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

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
                title = { Text("Что смотрим сегодня?") },
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentAlignment = Alignment.Center
            ) {
                // Фейковые карточки
                repeat(3) { index ->
                    Card(
                        modifier = Modifier
                            .size(200.dp, 300.dp)
                            .graphicsLayer {
                                rotationZ = (index - 1) * 5f
                                translationX = if (isShuffling) offset * (index + 1) else 0f
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = (index * 2).dp)
                    ) {}
                }

                AnimatedContent(
                    targetState = result,
                    transitionSpec = { (fadeIn() + scaleIn()).togetherWith(fadeOut() + scaleOut()) }
                ) { movie ->
                    if (movie == null) {
                        Card(
                            modifier = Modifier.size(220.dp, 320.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    } else {
                        BigMovieCard(movieModel = movie, onClick = {onMovieClick(movie.id)})
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    isShuffling = true
                    result = null

                    // Имитируем задержку, в реальности будем делать запросик к кэшу
                    Handler(Looper.getMainLooper()).postDelayed({
                        isShuffling = false
                        result = movies.random()
                    }, 1500)
                },
                enabled = !isShuffling,
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 32.dp)
            ) {
                if (isShuffling) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("Выбираем фильм...")
                } else {
                    Text("Удиви меня!")
                }
            }
        }
    }
}

@Preview
@Composable
fun RandomMovieScreenPreview() {
    RandomMovieScreen(onBackClick = {}, onMovieClick = {})
}