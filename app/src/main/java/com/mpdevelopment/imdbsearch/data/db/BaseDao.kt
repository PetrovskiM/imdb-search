package com.mpdevelopment.imdbsearch.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<E, Filter> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: E)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<E>)

    suspend fun get(query: Filter): E

    suspend fun getLike(query: Filter): List<@JvmSuppressWildcards E>

    @Update
    suspend fun update(item: E)

    @Update
    suspend fun update(items: List<E>)

    @Delete
    suspend fun delete(item: E)

    @Delete
    suspend fun delete(items: List<E>)
}