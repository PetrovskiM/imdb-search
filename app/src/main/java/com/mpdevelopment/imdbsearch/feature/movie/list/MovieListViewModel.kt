package com.mpdevelopment.imdbsearch.feature.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.feature.movie.list.state.Action
import com.mpdevelopment.imdbsearch.feature.movie.list.state.MovieListState
import com.mpdevelopment.imdbsearch.model.MovieBasics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieBasicsRepo: DataSource<MovieBasics, String>) :
    ViewModel() {

    private var textChangeCountDownJob: Job = SupervisorJob()
    private val _state = MutableStateFlow<MovieListState>(MovieListState.Empty)
    val state: StateFlow<MovieListState>
        get() = _state
    private var currentMovies = listOf<MovieBasics>()
    private var currentQuery = ""

    private suspend fun processState(state: MovieListState) {
        _state.emit(state)
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                currentMovies = movieBasicsRepo.getLike(query)
                processState(MovieListState.Display(currentMovies))
            } catch (e: Exception) {
                processState(MovieListState.Empty)
            }
        }
    }

    suspend fun handleAction(action: Action) {
        when (action) {
            is Action.Search -> {
                if (action.query == currentQuery) {
                    processState(MovieListState.Display(currentMovies))
                } else {
                    handleTextChange(action.query)
                }
            }
        }
    }

    private fun handleTextChange(query: String) {
        textChangeCountDownJob.cancel()
        textChangeCountDownJob = viewModelScope.launch {
            if (query.isNotBlank()) {
                currentQuery = query
                processState(MovieListState.Loading)
                delay(500)
                searchMovies(query)
            } else {
                processState(MovieListState.Empty)
            }
        }
    }
}