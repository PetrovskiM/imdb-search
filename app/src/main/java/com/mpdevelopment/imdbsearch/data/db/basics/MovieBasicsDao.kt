package com.mpdevelopment.imdbsearch.data.db.basics

import androidx.room.Dao
import androidx.room.Query
import com.mpdevelopment.imdbsearch.data.db.BaseDao
import com.mpdevelopment.imdbsearch.model.MovieBasics

@Dao
abstract class MovieBasicsDao : BaseDao<MovieBasics, String> {

    @Query("SELECT * FROM movie_basics WHERE name LIKE '%' || :query || '%'")
    abstract override suspend fun getLike(query: String): List<MovieBasics>

    @Query("SELECT * FROM movie_basics WHERE name LIKE :query")
    abstract override suspend fun get(query: String): MovieBasics
}