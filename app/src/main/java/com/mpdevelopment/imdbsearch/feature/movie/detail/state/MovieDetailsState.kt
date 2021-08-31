package com.mpdevelopment.imdbsearch.feature.movie.detail.state

import com.mpdevelopment.imdbsearch.model.MovieDetails

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Display(val movie: MovieDetails) : MovieDetailsState()
    object Error : MovieDetailsState()
}