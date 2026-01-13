package ru.ae.watchme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.domain.repository.MovieRepository

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())

    private val _errorMessage = MutableStateFlow<String?>(null)

    // Комбинируем состояние. В будущем логика поиска будет работать через API.
    // Хоспаде иисусе...
    val state: StateFlow<MovieListState> =
        combine(_movies, _query, _errorMessage) { movies, query, error ->
            when {
                // Есть ошибка
                error != null -> MovieListState.Error(error)

                // Нет данных
                movies.isEmpty() -> MovieListState.Loading

                // Фильтрация поиском
                else -> {
                    val filtered =
                        if (query.isEmpty()) {
                            movies
                        } else {
                            movies.filter { it.name.contains(query, ignoreCase = true) }
                        }

                    MovieListState.Success(filtered)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, MovieListState.Loading)

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _errorMessage.value = null
            try {
                val movies = repository.getMovies(pageNum = 1)
                _movies.value = movies
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ой ой ой, что-то страшное..."
            }
        }
    }

    fun search(query: String) {
        _query.value = query
    }
}