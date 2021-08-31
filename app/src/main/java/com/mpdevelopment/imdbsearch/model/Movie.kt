package com.mpdevelopment.imdbsearch.model

import androidx.room.Embedded
import androidx.room.Relation
import com.mpdevelopment.imdbsearch.data.common.DataItem

data class Movie(
    @Embedded
    val movieBasics: MovieBasics,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val movieDetails: MovieDetails
) : DataItem<String, String> {

    override fun getItemId(): String = movieBasics.id

    override fun getFilter(): String = movieBasics.name
}
