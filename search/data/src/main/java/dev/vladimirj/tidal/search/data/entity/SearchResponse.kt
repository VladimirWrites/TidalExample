package dev.vladimirj.tidal.search.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SearchResponse(
    @Json(name = "data")
    val data: List<RemoteArtist>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "next")
    val next: String?
)