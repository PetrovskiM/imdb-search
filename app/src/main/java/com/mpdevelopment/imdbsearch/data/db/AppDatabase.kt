package com.mpdevelopment.imdbsearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mpdevelopment.imdbsearch.data.db.basics.MovieBasicsDao
import com.mpdevelopment.imdbsearch.data.db.basics.MovieBasicsTypeConverter
import com.mpdevelopment.imdbsearch.data.db.details.MovieDetailsDao
import com.mpdevelopment.imdbsearch.data.db.details.MovieDetailsTypeConverter
import com.mpdevelopment.imdbsearch.model.MovieBasics
import com.mpdevelopment.imdbsearch.model.MovieDetails

@Database(
    entities = [MovieBasics::class, MovieDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MovieBasicsTypeConverter::class, MovieDetailsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "movies.db"
    }

    abstract fun movieBasicsDao(): MovieBasicsDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}