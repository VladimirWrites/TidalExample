package dev.vladimirj.tidal.search.domain.repo

import dev.vladimirj.tidal.search.domain.DomainResult

interface ArtistRepository {
    suspend fun searchForArtists(searchTerm: String): DomainResult
    suspend fun getMoreSearchResults(url: String): DomainResult
    suspend fun getRecordings(artistId: Long): DomainResult
    suspend fun getMoreRecordings(url: String): DomainResult
    suspend fun getTracks(albumId: Long): DomainResult
}