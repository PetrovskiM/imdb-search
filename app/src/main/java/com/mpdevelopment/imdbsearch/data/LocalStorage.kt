package com.mpdevelopment.imdbsearch.data

import com.mpdevelopment.imdbsearch.data.common.DataSource
import com.mpdevelopment.imdbsearch.data.db.BaseDao
import javax.inject.Inject

class LocalStorage<T, Filter> @Inject constructor(
    private val dao: BaseDao<T, Filter>,
    private val cache: DataSource<T, Filter>
) : DataSource<T, Filter> {

    override suspend fun get(query: Filter): T = cache.get(query) ?: dao.get(query)

    override suspend fun getLike(query: Filter): List<T> {
        val items = cache.getLike(query)
        return if (items.isEmpty()) {
            dao.getLike(query)
        } else {
            items
        }
    }

    override suspend fun save(item: T) {
        dao.insert(item)
        cache.save(item)
    }

    override suspend fun save(items: List<T>) {
        dao.insert(items)
        cache.save(items)
    }

    override suspend fun update(item: T) {
        dao.update(item)
        cache.update(item)
    }

    override suspend fun update(items: List<T>) {
        dao.update(items)
        cache.update(items)
    }

    override suspend fun delete(item: T) {
        dao.delete(item)
        cache.delete(item)
    }

    override suspend fun delete(items: List<T>) {
        dao.delete(items)
        cache.delete(items)
    }
}