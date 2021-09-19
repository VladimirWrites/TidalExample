package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import javax.inject.Inject

class GetAlbums @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val filterAlbums: FilterAlbums
) {
    suspend operator fun invoke(artistId: Long): DomainResult {
        return when (val recordings = artistRepository.getRecordings(artistId)) {
            is DomainResult.Success<*> -> filterAlbums(recordings)
            is DomainResult.Error -> recordings
        }
    }
}