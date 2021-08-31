package com.mpdevelopment.imdbsearch.feature.movie.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.feature.movie.detail.state.MovieDetailsState
import com.mpdevelopment.imdbsearch.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//Assisted Inject isn't working nicely with interfaces so the string id will be set with a setter
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: DataSource<MovieDetails, String>
) : ViewModel() {

    private lateinit var currentMovie: MovieDetails
    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val state: StateFlow<MovieDetailsState>
        get() = _state

    private suspend fun processState(state: MovieDetailsState) {
        _state.emit(state)
    }

    fun loadMovie(movieId: String, movieName: String) {
        viewModelScope.launch {
            try {
                currentMovie =
                    movieRepository.getLike(movieName).first { it.movieId == movieId }
                processState(MovieDetailsState.Display(currentMovie))
            } catch (e: Exception) {
                processState(MovieDetailsState.Error)
            }
        }
    }
}