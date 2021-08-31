package com.mpdevelopment.imdbsearch.data.db.details

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mpdevelopment.imdbsearch.model.Actor
import com.mpdevelopment.imdbsearch.model.TitleType

class MovieDetailsTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun serializeTitleType(titleType: TitleType?): String = gson.toJson(titleType)

    @TypeConverter
    fun deserializeTitleType(titleType: String): TitleType? =
        gson.fromJson(titleType, TitleType::class.java)

    @TypeConverter
    fun serializeActors(actors: List<Actor>?): String = gson.toJson(actors)

    @TypeConverter
    fun deserializeActors(actors: String): List<Actor>? {
        val type = object : TypeToken<List<Actor>>() {}.type
        return gson.fromJson(actors, type)
    }
}