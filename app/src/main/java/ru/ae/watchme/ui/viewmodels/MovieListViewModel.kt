package ru.ae.watchme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.domain.repository.MovieRepository

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val state = _state.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _state.value = MovieListState.Loading
            try {
                val movies = repository.getMovies(pageNum = 1)
                _state.value = MovieListState.Success(movies)
            } catch (e: Exception) {
                _state.value = MovieListState.Error(e.message ?: "Что-то вообще не то...")
            }
        }
    }

    fun search(query: String) {
        // TODO
    }
}