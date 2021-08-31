package com.mpdevelopment.imdbsearch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mpdevelopment.imdbsearch.data.common.DataItem

//If we want and the model starts getting messy or needs different behavior if its api / db
//we can separate out a MovieBasicsApi model MovieBasics db model each containing its respective
//annotations and methods
@Entity(tableName = "movie_basics")
data class MovieBasics(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("i")
    val poster: Poster?,
    @SerializedName("l")
    val name: String,
    @SerializedName("q")
    val category: MovieCategory?,
    @SerializedName("rank")
    val rank: Int,
    @ColumnInfo(name = "featured_actors")
    @SerializedName("s")
    val featuredActors: String,
    @SerializedName("y")
    val year: Int,
    @ColumnInfo(name = "years_active")
    @SerializedName("yr")
    val yearsActive: String?,
) : DataItem<String, String> {
    override fun getItemId(): String = id

    override fun getFilter(): String = name
}
