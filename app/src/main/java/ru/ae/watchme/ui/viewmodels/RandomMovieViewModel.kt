package ru.ae.watchme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ae.watchme.domain.repository.MovieRepository

class RandomMovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow<RandomMovieState>(RandomMovieState.Idle)
    val state = _state.asStateFlow()

    fun pick() {
        viewModelScope.launch {
            _state.value = RandomMovieState.Shuffling

            // Задержка на 1 секунду
            delay(1000)

            try {
                // TODO: Брать фильмы из избранного

                val movies = repository.getMovies(1)

                if (movies.isNotEmpty()) {
                    _state.value = RandomMovieState.Success(movies.random())
                } else {
                    _state.value = RandomMovieState.Empty
                }
            } catch (e: Exception) {
                _state.value = RandomMovieState.Empty
            }
        }
    }
}