package com.mpdevelopment.imdbsearch.feature.movie.list.state

import com.mpdevelopment.imdbsearch.model.MovieBasics

sealed class MovieListState {
    object Empty : MovieListState()
    object Loading : MovieListState()
    data class Display(val movies: List<MovieBasics>) : MovieListState()
}