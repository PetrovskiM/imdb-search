package com.mpdevelopment.imdbsearch.network.service

import com.mpdevelopment.imdbsearch.model.api.MovieDetailsResponse
import com.mpdevelopment.imdbsearch.model.api.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("auto-complete")
    suspend fun getMovies(@Query("q") query: String): MovieSearchResponse

    @GET("title/find")
    suspend fun getDetailedMovies(@Query("q") query: String): MovieDetailsResponse
}