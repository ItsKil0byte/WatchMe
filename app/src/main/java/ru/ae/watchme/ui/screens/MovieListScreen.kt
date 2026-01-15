package ru.ae.watchme.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    isFavoriteMode: Boolean,
    onSearch: (String) -> Unit,
    onLoad: () -> Unit,
    onToggleFavorite: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onRandomClick: () -> Unit
) {

    // Да, надо было сразу слушать Никиту и делить на три функции, а не на две...

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watch Me!") },
                actions = {
                    IconButton(onClick =  onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavoriteMode) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Filled.FavoriteBorder
                            },
                            contentDescription = null,
                            tint = if (isFavoriteMode) {
                                Color.Red
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
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
                        itemsIndexed(state.movies) { index, movie ->
                            MovieCard(movie = movie, onClick = { onMovieClick(movie.id)})

                            if (index >= state.movies.size - 1) {
                                onLoad()
                            }
                        }
                    }
                }
            }

            DockedSearchBar(
                shadowElevation = 4.dp,
                expanded = false, // Закрыл выпадение в SearchBar, мне лень писать это всё заново
                onExpandedChange = { },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { onSearch(it) },
                        onSearch = { },
                        expanded = false,
                        onExpandedChange = { },
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
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(0.9f)
                    .padding(top = 8.dp)
            ) {
                /*
                * Решил избавиться от выпадающего контейнера в списке. В текущей реализации он
                * простро мешался, перекрывал фильмы, а ListItem не давал достаточной информации.
                * Чтобы не запариваться с переделкой - просто убрал всё что связано с expanded.
                * Да, помоему не очень то и правильно, но, ресурс сейчас ограничен, так что не бейте.
                */
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
            _name = "Интерстеллар",
            _description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
            _shortDescription = "Фантастический эпос о путешествии через червоточину",
            _year = 2014,
            _genres = listOf("Фантастика", "Драма", "Приключения"),
            _posterUrl = "...",
            _previewUrl = "...",
            _rating = 8.6,
            _ageRating = 16
        ),
        Movie(
            id = 2,
            _name = "1+1",
            _description = "Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, — молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
            _shortDescription = "Трогательная комедия о неожиданной дружбе",
            _year = 2011,
            _genres = listOf("Драма", "Комедия", "Биография"),
            _posterUrl = "...",
            _previewUrl = "...",
            _rating = 8.8,
            _ageRating = 16
        ),
        Movie(
            id = 3,
            _name = "Начало",
            _description = "Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим.",
            _shortDescription = "Триллер о проникновении в сны",
            _year = 2010,
            _genres = listOf("Фантастика", "Боевик", "Триллер"),
            _posterUrl = "...",
            _previewUrl = "...",
            _rating = 8.7,
            _ageRating = 12
        ),
        Movie(
            id = 4,
            _name = "Король Лев",
            _description = "История взросления львенка Симбы, претендента на престол саванны, который вынужден бороться за свои права после того, как его дядя Шрам совершает предательство.",
            _shortDescription = "Анимационный шедевр о жизни в саванне",
            _year = 1994,
            _genres = listOf("Мультфильм", "Мюзикл", "Драма"),
            _posterUrl = "...",
            _previewUrl = "...",
            _rating = 8.5,
            _ageRating = 0
        ),
        Movie(
            id = 5,
            _name = "Побег из Шоушенка",
            _description = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки.",
            _shortDescription = "Драма о надежде в тюремных стенах",
            _year = 1994,
            _genres = listOf("Драма"),
            _posterUrl = "...",
            _previewUrl = "...",
            _rating = 9.3,
            _ageRating = 16
        )
    )

    MovieListContent(
        state = MovieListState.Success(previewMovies),
        query = "",
        isFavoriteMode = false,
        onSearch = {},
        onLoad = {},
        onMovieClick = {},
        onToggleFavorite = {},
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
    val isFavoriteMode by viewModel.isFavoriteMode.collectAsState()

    MovieListContent(
        state = state,
        query = query,
        isFavoriteMode = isFavoriteMode,
        onSearch = { viewModel.search(it) },
        onLoad = { viewModel.loadNext() },
        onMovieClick = onMovieClick,
        onToggleFavorite = { viewModel.toggleFavoritesMode() },
        onRandomClick = onRandomClick
    )
}