package com.mpdevelopment.imdbsearch.model

import com.google.gson.annotations.SerializedName

enum class TitleType {
    @SerializedName("movie")
    MOVIE,

    @SerializedName("tvEpisode")
    TV_EPISODE
}
