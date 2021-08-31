package com.mpdevelopment.imdbsearch.feature.movie.list.state

sealed class Action {
    data class Search(val query: String) : Action()
}
