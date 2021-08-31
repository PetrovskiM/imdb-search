package com.mpdevelopment.imdbsearch.data.cache

import com.mpdevelopment.imdbsearch.data.common.DataItem
import com.mpdevelopment.imdbsearch.data.common.DataSource
import java.util.concurrent.ConcurrentHashMap

//We can add a CacheConfig describing a maxSize, removeAfterDate or any other config especially if
//there are lots of objects and they are expensive so we don't cause an OutOfMemoryException
//for the sake of this demo I will keep it simple, if needed i can adjust it
class InMemoryCache<T : DataItem<Id, Filter>, Id, Filter> : DataSource<T, Filter> {

    private val map = ConcurrentHashMap<Id, T>()

    override suspend fun get(query: Filter): T? =
        map.values.toList().firstOrNull { query == it.getFilter() }

    override suspend fun getLike(query: Filter): List<T> =
        map.values.toList().filter { query == it.getFilter() }

    override suspend fun save(item: T) {
        map[item.getItemId()] = item
    }

    override suspend fun save(items: List<T>) {
        items.forEach { map[it.getItemId()] = it }
    }

    override suspend fun update(item: T) = save(item)

    override suspend fun update(items: List<T>) = save(items)

    override suspend fun delete(item: T) {
        map.remove(item.getItemId())
    }

    override suspend fun delete(items: List<T>) {
        items.forEach { map.remove(it.getItemId()) }
    }
}