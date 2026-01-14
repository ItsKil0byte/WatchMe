package ru.ae.watchme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.domain.repository.MovieRepository

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val state: StateFlow<MovieListState> = _query
        .debounce(500L)
        .distinctUntilChanged()
        .flatMapLatest {
            flow {
                emit(MovieListState.Loading)

                try {
                    val movies = if (it.isEmpty()) {
                        repository.getMovies(1)
                    } else {
                        repository.searchMovie(pageNum = 1, query = it)
                    }
                    emit(MovieListState.Success(movies))
                } catch (e: Exception) {
                    emit(MovieListState.Error(e.message ?: "Ой ой ой, что-то не так..."))
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, MovieListState.Loading)

    fun search(query: String) {
        _query.value = query
    }
}