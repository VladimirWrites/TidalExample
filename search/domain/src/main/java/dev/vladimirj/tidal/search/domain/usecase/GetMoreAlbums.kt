package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.DomainResult
import javax.inject.Inject

class GetMoreAlbums @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val filterAlbums: FilterAlbums
) {
    suspend operator fun invoke(url: String): DomainResult {
        return when(val recordings = artistRepository.getMoreRecordings(url)){
            is DomainResult.Success<*> -> filterAlbums(recordings)
            is DomainResult.Error -> recordings
        }
    }
}