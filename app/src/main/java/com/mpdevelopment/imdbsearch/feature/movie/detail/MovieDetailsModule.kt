package com.mpdevelopment.imdbsearch.feature.movie.detail

import com.mpdevelopment.imdbsearch.common.di.module.IoDispatcher
import com.mpdevelopment.imdbsearch.data.LocalStorage
import com.mpdevelopment.imdbsearch.data.cache.InMemoryCache
import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.data.db.BaseDao
import com.mpdevelopment.imdbsearch.data.repository.movie.details.MovieDetailsRepository
import com.mpdevelopment.imdbsearch.model.MovieDetails
import com.mpdevelopment.imdbsearch.network.service.MovieService
import com.mpdevelopment.imdbsearch.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object MovieDetailsModule {
    @Provides
    @ViewModelScoped
    fun provideDetailsRepository(
        movieService: MovieService,
        @Named("detailsLocalStorage") localStorage: DataSource<MovieDetails, String>,
        @IoDispatcher dispatcher: CoroutineDispatcher,
        networkUtil: NetworkUtil
    ): DataSource<MovieDetails, String> =
        MovieDetailsRepository(movieService, localStorage, dispatcher, networkUtil)

    @Provides
    @ViewModelScoped
    @Named("detailsLocalStorage")
    fun provideDetailsLocalStorage(
        dao: BaseDao<MovieDetails, String>,
        @Named("detailsLocalCache") cache: DataSource<MovieDetails, String>
    ): DataSource<MovieDetails, String> =
        LocalStorage(dao, cache)

    @Provides
    @ViewModelScoped
    @Named("detailsLocalCache")
    fun provideDetailsCache(): DataSource<MovieDetails, String> = InMemoryCache()
}