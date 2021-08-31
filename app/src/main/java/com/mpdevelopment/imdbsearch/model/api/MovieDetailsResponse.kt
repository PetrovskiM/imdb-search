package com.mpdevelopment.imdbsearch.model.api

import com.google.gson.annotations.SerializedName
import com.mpdevelopment.imdbsearch.model.MovieDetails

data class MovieDetailsResponse(
    @SerializedName("query") val query: String,
    @SerializedName("results") val movies: List<MovieDetails>,
)
