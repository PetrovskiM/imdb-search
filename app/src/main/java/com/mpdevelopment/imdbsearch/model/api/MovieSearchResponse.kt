package com.mpdevelopment.imdbsearch.model.api

import com.google.gson.annotations.SerializedName
import com.mpdevelopment.imdbsearch.model.MovieBasics

data class MovieSearchResponse(
    @SerializedName("d") val movies: List<MovieBasics>,
    @SerializedName("q") val query: String,
    @SerializedName("v") val page: String
)
