package dev.vladimirj.tidal.search.domain.entity

data class Recording(
    val id: Long,
    val title: String,
    val cover: String,
    val recordType: String
)