package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.SearchArtistsResult
import javax.inject.Inject

class SearchArtists @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(searchTerm: String): SearchArtistsResult {
        return artistRepository.searchForArtists(searchTerm)
    }
}