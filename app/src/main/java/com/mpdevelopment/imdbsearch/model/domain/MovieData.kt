package com.mpdevelopment.imdbsearch.model.domain

import androidx.room.Embedded
import androidx.room.Relation
import com.mpdevelopment.imdbsearch.model.MovieBasics
import com.mpdevelopment.imdbsearch.model.MovieDetails

data class MovieData(
    @Embedded
    val movieBasics: MovieBasics,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val movieDetails: MovieDetails
)