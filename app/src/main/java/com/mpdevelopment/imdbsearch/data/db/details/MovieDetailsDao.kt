package com.mpdevelopment.imdbsearch.data.db.details

import androidx.room.Dao
import androidx.room.Query
import com.mpdevelopment.imdbsearch.data.db.BaseDao
import com.mpdevelopment.imdbsearch.model.MovieDetails

@Dao
abstract class MovieDetailsDao : BaseDao<MovieDetails, String> {

    @Query("SELECT * FROM movie_details WHERE title LIKE '%' || :query || '%'")
    abstract override suspend fun getLike(query: String): List<MovieDetails>

    @Query("SELECT * FROM movie_details WHERE title LIKE :query")
    abstract override suspend fun get(query: String): MovieDetails
}