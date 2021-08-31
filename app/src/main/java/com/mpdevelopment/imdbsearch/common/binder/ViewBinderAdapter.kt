package com.mpdevelopment.imdbsearch.common.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.ArrayMap

class ViewBinderAdapter {

    val binders = ArrayMap<Class<*>, ViewBinder<*, *>>()

    inline fun <reified Type> addBinder(viewBinder: ViewBinder<*, *>): ViewBinderAdapter {
        val type = Type::class.java
        binders[type] = viewBinder
        return this
    }

    fun getItemViewType(item: Any): Int {
        return binders.indexOfKey(item.javaClass)
    }

    private fun getBinderForViewType(viewType: Int): ViewBinder<BinderViewHolder, Any> {
        return binders.valueAt(viewType) as ViewBinder<BinderViewHolder, Any>
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = getBinderForViewType(viewType)
        return binder.createViewHolder(parent, inflater)
    }

    fun onBindViewHolder(
        holder: BinderViewHolder,
        data: Any,
        selectedData: Any? = null,
        lastFromSection: Boolean? = null
    ) {
        val binder = getBinderForViewType(getItemViewType(data))
        selectedData?.let {
            if (it.javaClass != data.javaClass) {
                binder.bindViewHolder(holder, data, lastFromSection = lastFromSection)
            } else {
                binder.bindViewHolder(holder, data, selectedData, lastFromSection)
            }
        } ?: binder.bindViewHolder(holder, data, lastFromSection = lastFromSection)
    }

}