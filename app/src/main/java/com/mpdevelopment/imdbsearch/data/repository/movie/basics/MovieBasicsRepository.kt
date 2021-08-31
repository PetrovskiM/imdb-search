package com.mpdevelopment.imdbsearch.data.repository.movie.basics

import com.mpdevelopment.imdbsearch.common.di.module.IoDispatcher
import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.model.MovieBasics
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
class MovieBasicsRepository @Inject constructor(
    private val movieService: MovieService,
    @Named("basicsLocalStorage") private val localStorage: DataSource<MovieBasics, String>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val networkUtil: NetworkUtil
) : DataSource<MovieBasics, String> {

    override suspend fun get(query: String): MovieBasics? = withContext(dispatcher) {
        localStorage.get(query)
            ?: movieService.getMovies(query).movies.firstOrNull { it.name == query }
    }

    //We could play around with this i.e how we want the logic to be, it will be based on a
    //business logic decision, whether we always get from the cache if its not empty, or only if offline
    override suspend fun getLike(query: String): List<MovieBasics> = withContext(dispatcher) {
        val localItems = localStorage.getLike(query)
        return@withContext if (!networkUtil.isOnline) {
            localItems
        } else {
            val moviesBasics = movieService.getMovies(query)
            localStorage.save(moviesBasics.movies)
            moviesBasics.movies
        }
    }

    override suspend fun save(item: MovieBasics) =
        withContext(dispatcher) { localStorage.save(item) }

    override suspend fun save(items: List<MovieBasics>) =
        withContext(dispatcher) { localStorage.save(items) }

    override suspend fun update(item: MovieBasics) =
        withContext(dispatcher) { localStorage.update(item) }

    override suspend fun update(items: List<MovieBasics>) =
        withContext(dispatcher) { localStorage.update(items) }

    override suspend fun delete(item: MovieBasics) =
        withContext(dispatcher) { localStorage.delete(item) }

    override suspend fun delete(items: List<MovieBasics>) =
        withContext(dispatcher) { localStorage.delete(items) }
}
