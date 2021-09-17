package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.DomainResult
import javax.inject.Inject

class GetTracks @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(albumId: Long): DomainResult {
        return artistRepository.getTracks(albumId)
    }
}