package ru.ae.watchme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.domain.repository.MovieRepository

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _isFavoriteMode = MutableStateFlow(false)
    val isFavoriteMode = _isFavoriteMode.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)

    private val _loadedMovies = MutableStateFlow<List<Movie>>(emptyList())
    private var page = 1
    private var isLastPage = false
    private var isPagination = false


    /*
    * Переделываю логику поиска уже пятый раз. Не нравица.
    * Люди на стеке умные. Нравица.
    */

    val state: StateFlow<MovieListState> =
        combine(_query, _isFavoriteMode, _loadedMovies, _error) { query, favoriteMode, movies, error ->
            when {
                error != null -> MovieListState.Error(error)
                favoriteMode && movies.isEmpty() -> MovieListState.Error("Список избранного пуст :(")
                !favoriteMode && movies.isEmpty() -> MovieListState.Loading
                else -> MovieListState.Success(movies)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, MovieListState.Loading)

    init {
        observeSearch()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            combine(_query, _isFavoriteMode) {query, favorite -> query to favorite}
                .debounce { (query, _) -> if (query.isEmpty()) 0L else 500L }
                .distinctUntilChanged()
                .collect { (query, favorite) ->
                    reset()
                    loadNext()
                }
        }
    }

    private fun reset() {
        page = 1
        isLastPage = false
        _error.value = null
        _loadedMovies.value = emptyList()
    }

    fun loadNext() {
        if (isPagination || isLastPage) {
            return
        }

        viewModelScope.launch {
            isPagination = true
            _error.value = null
            try {
                val movies = if (_isFavoriteMode.value) {
                    // Грузим из БД
                    isLastPage = true
                    repository.getAllMoviesFromDb()
                } else {
                    // Грузим из сети
                    val loadedMovies = if (_query.value.isEmpty()) {
                        repository.getMovies(page)
                    } else {
                        repository.searchMovie(pageNum = page, query = _query.value)
                    }

                    if (loadedMovies.isEmpty()) {
                        isLastPage = true
                    }

                    loadedMovies
                }

                _loadedMovies.value += movies
                page++
            } catch (e: Exception) {
                // Ну хз, поплачь об этом
                _error.value = e.message ?: "Ой ой ой"
            } finally {
                isPagination = false
            }
        }
    }

    fun search(query: String) {
        _query.value = query
    }

    fun toggleFavoritesMode() {
        _isFavoriteMode.value = !_isFavoriteMode.value
    }
}