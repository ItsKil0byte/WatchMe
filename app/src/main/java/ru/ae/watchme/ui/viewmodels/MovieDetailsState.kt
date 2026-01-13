package ru.ae.watchme.ui.viewmodels

import ru.ae.watchme.domain.model.Movie

sealed interface MovieDetailsState {
    data class Success(val movie: Movie, val isFavourite: Boolean) : MovieDetailsState
    data class Error(val message: String) : MovieDetailsState
    object Loading : MovieDetailsState
}