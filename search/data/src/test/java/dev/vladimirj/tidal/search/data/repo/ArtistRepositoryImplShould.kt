package dev.vladimirj.tidal.search.data.repo

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.data.SearchService
import dev.vladimirj.tidal.search.data.entity.RemoteAlbum
import dev.vladimirj.tidal.search.data.entity.RemoteArtist
import dev.vladimirj.tidal.search.data.entity.RemoteResponse
import dev.vladimirj.tidal.search.data.entity.RemoteTrack
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Album
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

private val ALBUMS_RESPONSE_STUB = RemoteResponse(
    data = listOf(
        RemoteAlbum(1, "test1", "url1"),
        RemoteAlbum(2, "test2", "url2"),
        RemoteAlbum(3, "test3", "url3")
    ),
    total = 42,
    next = "next.com"
)

private val ALBUMS_RESULT_STUB = DomainResult.Success(
    data = listOf(
        Album(1, "test1", "url1"),
        Album(2, "test2", "url2"),
        Album(3, "test3", "url3")
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
    fun getAlbums_usingSearchService() = runBlockingTest {
        val artistId = 1L
        whenever(searchService.getAlbums(artistId)).thenReturn(ALBUMS_RESPONSE_STUB)

        artistRepository.getAlbums(artistId)

        verify(searchService).getAlbums(artistId)
    }

    @Test
    fun mapAlbumResponse_toAlbumResult() = runBlockingTest {
        val artistId = 1L
        whenever(searchService.getAlbums(artistId)).thenReturn(ALBUMS_RESPONSE_STUB)

        val actual = artistRepository.getAlbums(artistId)

        assertThat(actual).isEqualTo(ALBUMS_RESULT_STUB)
    }

    @Test
    fun getMoreAlbums_usingSearchService() = runBlockingTest {
        val url = "https://test.com"
        whenever(searchService.getMoreAlbums(url)).thenReturn(ALBUMS_RESPONSE_STUB)

        artistRepository.getMoreAlbums(url)

        verify(searchService).getMoreAlbums(url)
    }

    @Test
    fun mapGetMoreAlbumsResponse_toAlbumResult() = runBlockingTest {
        val url = "https://test.com"
        whenever(searchService.getMoreAlbums(url)).thenReturn(ALBUMS_RESPONSE_STUB)

        val actual = artistRepository.getMoreAlbums(url)

        assertThat(actual).isEqualTo(ALBUMS_RESULT_STUB)
    }

    @Test
    fun mapTrackResponse_toTrackResult() = runBlockingTest {
        val albumId = 1L
        whenever(searchService.getTracks(albumId)).thenReturn(TRACKS_RESPONSE_STUB)

        val actual = artistRepository.getTracks(albumId)

        assertThat(actual).isEqualTo(TRACKS_RESULT_STUB)
    }
}