package ru.ae.watchme.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.ae.watchme.R
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.ui.components.MovieCard
import ru.ae.watchme.ui.viewmodels.MovieListState
import ru.ae.watchme.ui.viewmodels.MovieListViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieListContent(
    state: MovieListState,
    query: String,
    onSearch: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onRandomClick: () -> Unit
) {

    // Да, надо было сразу слушать Никиту и делить на три функции, а не на две...

    var isActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watch Me!") },
                actions = {
                    IconButton(onClick = onRandomClick) {
                        Icon(
                            painter = painterResource(R.drawable.casino_24),
                            contentDescription = "Открыть колесо рандома"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (state) {
                is MovieListState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is MovieListState.Error -> {
                    Text(text = state.message, modifier = Modifier.align(Alignment.Center))
                }

                is MovieListState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 80.dp,
                            bottom = 8.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                    ) {
                        items(state.movies) {
                            MovieCard(movie = it, onClick = { onMovieClick(it.id) })
                        }
                    }
                }
            }

            DockedSearchBar(
                shadowElevation = 4.dp,
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { onSearch(it) },
                        onSearch = { isActive = false },
                        expanded = isActive,
                        onExpandedChange = { isActive = it },
                        placeholder = { Text("Найти фильмы...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { onSearch("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = null)
                                }
                            }
                        }
                    )
                },
                expanded = isActive,
                onExpandedChange = { isActive = it },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(0.9f)
                    .padding(top = 8.dp)
            ) {
                if (state is MovieListState.Success) {
                    val suggestion = state.movies.take(5)

                    suggestion.forEach {
                        ListItem(
                            headlineContent = { Text(it.name) },
                            modifier = Modifier.clickable {
                                onSearch(it.name)
                                isActive = false
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent
                            )
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    val previewMovies = listOf(
        Movie(
            id = 1,
            name = "Интерстеллар",
            description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
            shortDescription = "Фантастический эпос о путешествии через червоточину",
            year = 2014,
            genres = listOf("Фантастика", "Драма", "Приключения"),
            posterUrl = "...",
            previewUrl = "...",
            rating = 8.6,
            ageRating = 16
        ),
        Movie(
            id = 2,
            name = "1+1",
            description = "Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, — молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
            shortDescription = "Трогательная комедия о неожиданной дружбе",
            year = 2011,
            genres = listOf("Драма", "Комедия", "Биография"),
            posterUrl = "...",
            previewUrl = "...",
            rating = 8.8,
            ageRating = 16
        ),
        Movie(
            id = 3,
            name = "Начало",
            description = "Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим.",
            shortDescription = "Триллер о проникновении в сны",
            year = 2010,
            genres = listOf("Фантастика", "Боевик", "Триллер"),
            posterUrl = "...",
            previewUrl = "...",
            rating = 8.7,
            ageRating = 12
        ),
        Movie(
            id = 4,
            name = "Король Лев",
            description = "История взросления львенка Симбы, претендента на престол саванны, который вынужден бороться за свои права после того, как его дядя Шрам совершает предательство.",
            shortDescription = "Анимационный шедевр о жизни в саванне",
            year = 1994,
            genres = listOf("Мультфильм", "Мюзикл", "Драма"),
            posterUrl = "...",
            previewUrl = "...",
            rating = 8.5,
            ageRating = 0
        ),
        Movie(
            id = 5,
            name = "Побег из Шоушенка",
            description = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки.",
            shortDescription = "Драма о надежде в тюремных стенах",
            year = 1994,
            genres = listOf("Драма"),
            posterUrl = "...",
            previewUrl = "...",
            rating = 9.3,
            ageRating = 16
        )
    )

    MovieListContent(
        state = MovieListState.Success(previewMovies),
        query = "",
        onSearch = {},
        onMovieClick = {},
        onRandomClick = {}
    )
}

@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    onRandomClick: () -> Unit,
    viewModel: MovieListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()

    MovieListContent(
        state = state,
        query = query,
        onSearch = { viewModel.search(it) },
        onMovieClick = onMovieClick,
        onRandomClick = onRandomClick
    )
}