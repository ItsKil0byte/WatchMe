package ru.ae.watchme.ui.viewmodels

import ru.ae.watchme.domain.model.Movie

sealed interface MovieListState {
    data class Success(val movies: List<Movie>): MovieListState
    data class Error(val message: String): MovieListState

    object Loading: MovieListState
}