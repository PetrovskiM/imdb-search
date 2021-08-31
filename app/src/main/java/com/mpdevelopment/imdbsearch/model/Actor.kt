package com.mpdevelopment.imdbsearch.model

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("characters") val characters: List<String>
)
