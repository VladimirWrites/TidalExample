package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.DomainResult
import javax.inject.Inject

class SearchArtists @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(searchTerm: String): DomainResult {
        return artistRepository.searchForArtists(searchTerm)
    }
}