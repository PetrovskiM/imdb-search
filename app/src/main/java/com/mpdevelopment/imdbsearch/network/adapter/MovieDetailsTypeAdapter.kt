package com.mpdevelopment.imdbsearch.network.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mpdevelopment.imdbsearch.model.Actor
import com.mpdevelopment.imdbsearch.model.MovieDetails
import com.mpdevelopment.imdbsearch.model.Poster
import com.mpdevelopment.imdbsearch.model.TitleType
import java.lang.reflect.Type

class MovieDetailsTypeAdapter : JsonDeserializer<MovieDetails> {


    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): MovieDetails {

        val movieDetails = json.asJsonObject
        val primaryId = movieDetails.get("id").asString
        val idValueSplit = primaryId.trim('/').split('/')
        val movieId = idValueSplit.last()

        val poster: Poster? = if (movieDetails.has("image")) {
            val posterJson = movieDetails.get("image").asJsonObject
            val width = posterJson.get("width").asInt
            val height = posterJson.get("height").asInt
            val url = posterJson.get("url").asString
            Poster(height, width, url)
        } else {
            null
        }

        val titleType: TitleType? = if (movieDetails.has("titleType")) {
            context.deserialize(movieDetails.get("titleType"), TitleType::class.java)
        } else {
            null
        }

        val actorsType = object : TypeToken<List<Actor>>() {}.type
        val actors: List<Actor>? = if (movieDetails.has("principals")) {
            context.deserialize(movieDetails.getAsJsonArray("principals"), actorsType)
        } else {
            null
        }

        val runningTime: Int? = if (movieDetails.has("runningTimeInMinutes")) {
            movieDetails.get("runningTimeInMinutes").asInt
        } else {
            null
        }

        val year: String? = if (movieDetails.has("year")) {
            movieDetails.get("year").asString
        } else {
            null
        }

        val title: String? = if (movieDetails.has("title")) {
            movieDetails.get("title").asString
        } else {
            null
        }

        return MovieDetails(
            primaryId,
            movieId,
            poster,
            runningTime,
            title,
            titleType,
            year,
            actors
        )
    }
}