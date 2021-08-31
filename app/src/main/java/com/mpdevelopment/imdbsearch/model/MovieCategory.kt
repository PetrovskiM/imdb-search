package com.mpdevelopment.imdbsearch.model

import com.google.gson.annotations.SerializedName

enum class MovieCategory {
    @SerializedName("feature")
    FEATURE,

    @SerializedName("TV series")
    TV_SERIES
}
