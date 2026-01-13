package ru.ae.watchme.ui.screens

import android.graphics.pdf.models.ListItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.ae.watchme.R
import ru.ae.watchme.ui.components.MovieCard
import ru.ae.watchme.ui.viewmodels.MovieListState
import ru.ae.watchme.ui.viewmodels.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    onRandomWheelClick: () -> Unit,
    viewModel: MovieListViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()

    var isActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watch Me!") },
                actions = {
                    IconButton(onClick = onRandomWheelClick) {
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

            when (val currentState = state) {
                is MovieListState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is MovieListState.Error -> {
                    Text(text = currentState.message, modifier = Modifier.align(Alignment.Center))
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
                        items(currentState.movies) {
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
                        onQueryChange = { viewModel.search(it) },
                        onSearch = { isActive = false },
                        expanded = isActive,
                        onExpandedChange = { isActive = it },
                        placeholder = { Text("Найти фильмы...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { viewModel.search("") }) {
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
                    val suggestion = (state as MovieListState.Success).movies.take(5)

                    suggestion.forEach {
                        ListItem(
                            headlineContent = { Text(it.name) },
                            modifier = Modifier.clickable {
                                viewModel.search(it.name)
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
    MovieListScreen(onMovieClick = {}, onRandomWheelClick = {})
}