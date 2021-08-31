package com.mpdevelopment.imdbsearch.common.binder

import android.view.LayoutInflater
import android.view.ViewGroup

interface ViewBinder<ViewHolder : BinderViewHolder, Type> {
    fun createViewHolder(viewGroup: ViewGroup, inflater: LayoutInflater): ViewHolder
    fun bindViewHolder(
        viewHolder: ViewHolder,
        data: Type,
        selectedData: Type? = null,
        lastFromSection: Boolean? = null
    )
}