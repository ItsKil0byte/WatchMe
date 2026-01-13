package ru.ae.watchme.ui.viewmodels

import ru.ae.watchme.domain.model.Movie

sealed interface RandomMovieState {
    data class Success(val movie: Movie) : RandomMovieState

    object Idle : RandomMovieState
    object Shuffling: RandomMovieState
    object Empty: RandomMovieState // По идее состояние, если список избранного пуст
    // Хотя, мб сделаем и не только по избранному, а просто рандомный фильм?
}