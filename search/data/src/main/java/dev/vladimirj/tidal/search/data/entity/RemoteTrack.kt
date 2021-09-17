package dev.vladimirj.tidal.search.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteTrack(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "track_position")
    val trackPosition: Int,
    @Json(name = "disk_number")
    val diskNumber: Int
)