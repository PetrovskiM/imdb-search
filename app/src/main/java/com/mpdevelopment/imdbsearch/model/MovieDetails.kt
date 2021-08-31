package com.mpdevelopment.imdbsearch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mpdevelopment.imdbsearch.data.common.DataItem

@Entity(tableName = "movie_details")
data class MovieDetails(
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "movie_id")
    val movieId: String,
    @SerializedName("image")
    val image: Poster?,
    @SerializedName("runningTimeInMinutes")
    @ColumnInfo(name = "running_time_in_minutes")
    val runningTimeMinutes: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("titleType")
    val type: TitleType?,
    @SerializedName("year")
    val year: String?,
    @SerializedName("principals")
    val principals: List<Actor>?
) : DataItem<String, String> {
    override fun getItemId(): String = id

    override fun getFilter(): String = title ?: "unknown"
}
