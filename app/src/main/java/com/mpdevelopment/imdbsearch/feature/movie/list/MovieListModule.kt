package com.mpdevelopment.imdbsearch.feature.movie.list

import com.mpdevelopment.imdbsearch.common.di.module.IoDispatcher
import com.mpdevelopment.imdbsearch.data.LocalStorage
import com.mpdevelopment.imdbsearch.data.cache.InMemoryCache
import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.data.db.BaseDao
import com.mpdevelopment.imdbsearch.data.repository.movie.basics.MovieBasicsRepository
import com.mpdevelopment.imdbsearch.model.MovieBasics
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
object MovieListModule {

    @Provides
    @ViewModelScoped
    fun provideBasicsRepository(
        movieService: MovieService,
        @Named("basicsLocalStorage") localStorage: DataSource<MovieBasics, String>,
        @IoDispatcher dispatcher: CoroutineDispatcher,
        networkUtil: NetworkUtil
    ): DataSource<MovieBasics, String> =
        MovieBasicsRepository(movieService, localStorage, dispatcher, networkUtil)

    @Provides
    @ViewModelScoped
    @Named("basicsLocalStorage")
    fun provideBasicsLocalStorage(
        dao: BaseDao<MovieBasics, String>,
        @Named("basicsLocalCache") cache: DataSource<MovieBasics, String>
    ): DataSource<MovieBasics, String> =
        LocalStorage(dao, cache)

    @Provides
    @ViewModelScoped
    @Named("basicsLocalCache")
    fun provideBasicsCache(): DataSource<MovieBasics, String> = InMemoryCache()
}