package com.mpdevelopment.imdbsearch.model

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("imageUrl") val url: String
)