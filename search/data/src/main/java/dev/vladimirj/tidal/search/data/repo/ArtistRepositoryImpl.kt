package dev.vladimirj.tidal.search.data.repo

import dev.vladimirj.tidal.search.data.SearchService
import dev.vladimirj.tidal.search.data.entity.RemoteRecording
import dev.vladimirj.tidal.search.data.entity.RemoteArtist
import dev.vladimirj.tidal.search.data.entity.RemoteResponse
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Recording
import dev.vladimirj.tidal.search.domain.entity.Track

class ArtistRepositoryImpl(
    private val searchService: SearchService
) : ArtistRepository {
    override suspend fun searchForArtists(searchTerm: String): DomainResult {
        return getSearchResultsAndMapThem { searchService.search(searchTerm) }
    }

    override suspend fun getMoreSearchResults(url: String): DomainResult {
        return getSearchResultsAndMapThem { searchService.getMoreSearchResults(url) }
    }

    override suspend fun getRecordings(artistId: Long): DomainResult {
        return getRecordingsAndMapThem { searchService.getRecordings(artistId) }
    }

    override suspend fun getMoreRecordings(url: String): DomainResult {
        return getRecordingsAndMapThem { searchService.getMoreRecordings(url) }
    }

    override suspend fun getTracks(albumId: Long): DomainResult {
        return try {
            val result = searchService.getTracks(albumId)
            val listOfTrack = result.data.map {
                Track(
                    id = it.id,
                    title = it.title,
                    trackPosition = it.trackPosition,
                    diskNumber = it.diskNumber
                )
            }
            DomainResult.Success(listOfTrack, result.total, result.next)
        } catch (throwable: Throwable) {

            DomainResult.Error(throwable.getBestMessage())
        }
    }

    private suspend fun getSearchResultsAndMapThem(provider: (suspend () -> RemoteResponse<RemoteArtist>)): DomainResult {
        return try {
            val result = provider()
            val listOfArtists = result.data.map {
                Artist(
                    id = it.id,
                    name = it.name,
                    picture = it.picture
                )
            }
            DomainResult.Success(listOfArtists, result.total, result.next)
        } catch (throwable: Throwable) {
            DomainResult.Error(throwable.getBestMessage())
        }
    }

    private suspend fun getRecordingsAndMapThem(provider: (suspend () -> RemoteResponse<RemoteRecording>)): DomainResult {
        return try {
            val result = provider()
            val listOfAlbums = result.data.map {
                Recording(
                    id = it.id,
                    title = it.title,
                    cover = it.cover,
                    recordType = it.recordType
                )
            }
            DomainResult.Success(listOfAlbums, result.total, result.next)
        } catch (throwable: Throwable) {
            DomainResult.Error(throwable.getBestMessage())
        }
    }

    private fun Throwable.getBestMessage(): String =
        this.localizedMessage ?: this.message ?: "Something went wrong!"
}