package ru.ae.watchme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ae.watchme.domain.repository.MovieRepository

class MovieDetailsViewModel(
    private val id: Int,
    private val repository: MovieRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val state = _state.asStateFlow()

    init {
        loadDetails()
    }

    fun loadDetails() {
        viewModelScope.launch {
            _state.value = MovieDetailsState.Loading
            try {
                // TODO: Подставить метод с idшкой
                val movie = repository.getRandomMovie()

                // Ставим false пока нет сохранения в БД
                _state.value = MovieDetailsState.Success(movie, false)
            } catch (e: Exception) {
                _state.value =
                    MovieDetailsState.Error(e.message ?: "Ой ой ой, что-то совсем плохое...")
            }
        }
    }

    fun toggleFavourite() {
        val currentState = _state.value

        if (currentState is MovieDetailsState.Success) {
            val status = !currentState.isFavourite

            _state.value = currentState.copy(isFavourite = status)

            // TODO: Сохранить это в локальное БД
        }
    }
}