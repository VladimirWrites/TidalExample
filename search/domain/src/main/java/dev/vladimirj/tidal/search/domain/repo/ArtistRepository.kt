package dev.vladimirj.tidal.search.domain.repo

import dev.vladimirj.tidal.search.domain.SearchArtistsResult

interface ArtistRepository {
    suspend fun searchForArtists(searchTerm: String): SearchArtistsResult
    suspend fun getMoreSearchResults(url: String): SearchArtistsResult
}