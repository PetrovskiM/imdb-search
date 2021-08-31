package com.mpdevelopment.imdbsearch.data.common

interface DataItem<Id, Filter> {
    fun getItemId(): Id
    fun getFilter(): Filter
}