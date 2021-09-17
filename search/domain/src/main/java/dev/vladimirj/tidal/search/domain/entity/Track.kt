package dev.vladimirj.tidal.search.domain.entity

data class Track(
    val id: Long,
    val title: String,
    val trackPosition: Int,
    val diskNumber: Int
)