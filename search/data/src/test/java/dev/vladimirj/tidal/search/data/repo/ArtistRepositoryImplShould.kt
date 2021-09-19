package dev.vladimirj.tidal.search.data.repo

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.data.SearchService
import dev.vladimirj.tidal.search.data.entity.RemoteRecording
import dev.vladimirj.tidal.search.data.entity.RemoteArtist
import dev.vladimirj.tidal.search.data.entity.RemoteResponse
import dev.vladimirj.tidal.search.data.entity.RemoteTrack
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Recording
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.entity.Track
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

private val SEARCH_RESPONSE_STUB = RemoteResponse(
    data = listOf(
        RemoteArtist(1, "test1", "url1"),
        RemoteArtist(2, "test2", "url2"),
        RemoteArtist(3, "test3", "url3")
    ),
    total = 42,
    next = "next.com"
)

private val SEARCH_RESULT_RESPONSE_STUB = DomainResult.Success(
    data = listOf(
        Artist(1, "test1", "url1"),
        Artist(2, "test2", "url2"),
        Artist(3, "test3", "url3")
    ),
    totalSize = 42,
    next = "next.com"
)

private val RECORDING_RESPONSE_STUB = RemoteResponse(
    data = listOf(
        RemoteRecording(1, "test1", "url1", "album"),
        RemoteRecording(2, "test2", "url2", "single"),
        RemoteRecording(3, "test3", "url3", "album")
    ),
    total = 42,
    next = "next.com"
)

private val RECORDING_RESULT_STUB = DomainResult.Success(
    data = listOf(
        Recording(1, "test1", "url1", "album"),
        Recording(2, "test2", "url2", "single"),
        Recording(3, "test3", "url3", "album")
    ),
    totalSize = 42,
    next = "next.com"
)

private val TRACKS_RESPONSE_STUB = RemoteResponse(
    data = listOf(
        RemoteTrack(1, "test1", 1, 1),
        RemoteTrack(2, "test2", 2, 1),
        RemoteTrack(3, "test3", 1, 2)
    ),
    total = 42,
    next = "next.com"
)

private val TRACKS_RESULT_STUB = DomainResult.Success(
    data = listOf(
        Track(1, "test1", 1, 1),
        Track(2, "test2", 2, 1),
        Track(3, "test3", 1, 2)
    ),
    totalSize = 42,
    next = "next.com"
)

@ExperimentalCoroutinesApi
class ArtistRepositoryImplShould {

    private val searchService = mock<SearchService>()
    private val artistRepository = ArtistRepositoryImpl(searchService)

    @Test
    fun searchForArtists_usingSearchService() = runBlockingTest {
        val query = "test"
        whenever(searchService.search(query)).thenReturn(SEARCH_RESPONSE_STUB)

        artistRepository.searchForArtists(query)

        verify(searchService).search(query)
    }

    @Test
    fun mapSearchResponse_toResults() = runBlockingTest {
        val query = "test"
        whenever(searchService.search(query)).thenReturn(SEARCH_RESPONSE_STUB)

        val actual = artistRepository.searchForArtists(query)

        assertThat(actual).isEqualTo(SEARCH_RESULT_RESPONSE_STUB)
    }

    @Test
    fun getMoreSearchResults_usingSearchService() = runBlockingTest {
        val url = "https://test.com"
        whenever(searchService.getMoreSearchResults(url)).thenReturn(SEARCH_RESPONSE_STUB)

        artistRepository.getMoreSearchResults(url)

        verify(searchService).getMoreSearchResults(url)
    }

    @Test
    fun mapMoreSearchResultsResponse_toResults() = runBlockingTest {
        val url = "https://test.com"
        whenever(searchService.getMoreSearchResults(url)).thenReturn(SEARCH_RESPONSE_STUB)

        val actual = artistRepository.getMoreSearchResults(url)

        assertThat(actual).isEqualTo(SEARCH_RESULT_RESPONSE_STUB)
    }

    @Test
    fun getRecordings_usingSearchService() = runBlockingTest {
        val artistId = 1L
        whenever(searchService.getRecordings(artistId)).thenReturn(RECORDING_RESPONSE_STUB)

        artistRepository.getRecordings(artistId)

        verify(searchService).getRecordings(artistId)
    }

    @Test
    fun mapRecordingsResponse_toAlbumResult() = runBlockingTest {
        val artistId = 1L
        whenever(searchService.getRecordings(artistId)).thenReturn(RECORDING_RESPONSE_STUB)

        val actual = artistRepository.getRecordings(artistId)

        assertThat(actual).isEqualTo(RECORDING_RESULT_STUB)
    }

    @Test
    fun getMoreRecordings_usingSearchService() = runBlockingTest {
        val url = "https://test.com"
        whenever(searchService.getMoreRecordings(url)).thenReturn(RECORDING_RESPONSE_STUB)

        artistRepository.getMoreRecordings(url)

        verify(searchService).getMoreRecordings(url)
    }

    @Test
    fun mapGetMoreAlbumsResponse_toAlbumResult() = runBlockingTest {
        val url = "https://test.com"
        whenever(searchService.getMoreRecordings(url)).thenReturn(RECORDING_RESPONSE_STUB)

        val actual = artistRepository.getMoreRecordings(url)

        assertThat(actual).isEqualTo(RECORDING_RESULT_STUB)
    }

    @Test
    fun mapTrackResponse_toTrackResult() = runBlockingTest {
        val albumId = 1L
        whenever(searchService.getTracks(albumId)).thenReturn(TRACKS_RESPONSE_STUB)

        val actual = artistRepository.getTracks(albumId)

        assertThat(actual).isEqualTo(TRACKS_RESULT_STUB)
    }
}