package dev.vladimirj.tidal.search.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRecording(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "cover_big")
    val cover: String?,
    @Json(name = "record_type")
    val recordType: String
)