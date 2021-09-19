package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import javax.inject.Inject

class LoadMoreArtists @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(url: String): DomainResult {
        return artistRepository.getMoreSearchResults(url)
    }
}