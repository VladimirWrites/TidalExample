package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.DomainResult
import javax.inject.Inject

class GetAlbums @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(artistId: Long): DomainResult {
        return artistRepository.getAlbums(artistId)
    }
}