package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.DomainResult
import javax.inject.Inject

class GetMoreAlbums @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(url: String): DomainResult {
        return artistRepository.getMoreAlbums(url)
    }
}