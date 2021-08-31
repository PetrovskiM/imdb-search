package com.mpdevelopment.imdbsearch.data.db

import com.mpdevelopment.imdbsearch.model.MovieBasics
import com.mpdevelopment.imdbsearch.model.MovieDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMovieBasicsDao(appDatabase: AppDatabase): BaseDao<MovieBasics, String> =
        appDatabase.movieBasicsDao()

    @Provides
    fun provideMovieDetailsDao(appDatabase: AppDatabase): BaseDao<MovieDetails, String> =
        appDatabase.movieDetailsDao()
}