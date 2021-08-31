package com.mpdevelopment.imdbsearch.data.db.basics

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mpdevelopment.imdbsearch.model.MovieCategory
import com.mpdevelopment.imdbsearch.model.Poster

class MovieBasicsTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun serializePoster(poster: Poster?): String = gson.toJson(poster)

    @TypeConverter
    fun deserializePoster(poster: String): Poster? = gson.fromJson(poster, Poster::class.java)

    @TypeConverter
    fun serializeMovieCategory(movieCategory: MovieCategory?): String = gson.toJson(movieCategory)

    @TypeConverter
    fun deserializeMovieCategory(movieCategory: String): MovieCategory? =
        gson.fromJson(movieCategory, MovieCategory::class.java)
}