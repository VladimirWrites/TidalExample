package dev.vladimirj.tidal.search.domain

import dev.vladimirj.tidal.search.domain.entity.Artist

sealed class SearchArtistsResult {
    data class Success(val artists: List<Artist>, val totalSize: Int, val next: String?) : SearchArtistsResult()
    data class Error(val message: String) : SearchArtistsResult()
}