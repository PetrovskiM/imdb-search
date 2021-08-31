package com.mpdevelopment.imdbsearch.data.repository.movie.details

import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.model.MovieDetails
import com.mpdevelopment.imdbsearch.network.service.MovieService
import com.mpdevelopment.imdbsearch.util.NetworkUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

//We could also add a config here describing if the user wants to always fetch fresh data
//If we want access to the db only in offline mode
//All of this can some from a Settings screen that sets user preferences in a UserManager with a User
//The repository will still be simple and just get / save and delegate further
//We would inject the UserManager params into the local storage which will return a List<Movie>?
class MovieDetailsRepository @Inject constructor(
    private val movieService: MovieService,
    @Named("detailsLocalStorage") private val localStorage: DataSource<MovieDetails, String>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val networkUtil: NetworkUtil
) : DataSource<MovieDetails, String> {

    override suspend fun get(query: String): MovieDetails? = withContext(dispatcher) {
        localStorage.get(query)
            ?: movieService.getDetailedMovies(query).movies.firstOrNull { it.title == query }
    }

    override suspend fun getLike(query: String): List<MovieDetails> = withContext(dispatcher) {
        val localItems = localStorage.getLike(query)
        return@withContext if (!networkUtil.isOnline) {
            localItems
        } else {
            val moviesDetails = movieService.getDetailedMovies(query)
            localStorage.save(moviesDetails.movies)
            moviesDetails.movies
        }
    }

    override suspend fun save(item: MovieDetails) =
        withContext(dispatcher) { localStorage.save(item) }

    override suspend fun save(items: List<MovieDetails>) =
        withContext(dispatcher) { localStorage.save(items) }

    override suspend fun update(item: MovieDetails) =
        withContext(dispatcher) { localStorage.update(item) }

    override suspend fun update(items: List<MovieDetails>) =
        withContext(dispatcher) { localStorage.update(items) }

    override suspend fun delete(item: MovieDetails) =
        withContext(dispatcher) { localStorage.delete(item) }

    override suspend fun delete(items: List<MovieDetails>) =
        withContext(dispatcher) { localStorage.delete(items) }
}
