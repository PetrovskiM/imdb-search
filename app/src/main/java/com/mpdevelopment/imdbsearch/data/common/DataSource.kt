package com.mpdevelopment.imdbsearch.data.common

interface DataSource<T, Filter> {
    suspend fun get(query: Filter): T?
    suspend fun getLike(query: Filter): List<T>
    suspend fun save(item: T)
    suspend fun save(items: List<T>)
    suspend fun update(item: T)
    suspend fun update(items: List<T>)
    suspend fun delete(item: T)
    suspend fun delete(items: List<T>)
}