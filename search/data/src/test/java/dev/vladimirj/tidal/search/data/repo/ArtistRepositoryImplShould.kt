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
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class ArtistRepositoryImplShould {

    private val searchResponseStub = RemoteResponse(
        data = listOf(
            RemoteArtist(1, "test1", "url1"),
            RemoteArtist(2, "test2", "url2"),
            RemoteArtist(3, "test3", "url3")
        ),
        total = 42,
        next = "next.com"
    )

    private val searchArtistsResultStub = DomainResult.Success(
        data = listOf(
            Artist(1, "test1", "url1"),
            Artist(2, "test2", "url2"),
            Artist(3, "test3", "url3")
        ),
        totalSize = 42,
        next = "next.com"
    )

    private val albumsResponseStub = RemoteResponse(
        data = listOf(
            RemoteAlbum(1, "test1", "url1"),
            RemoteAlbum(2, "test2", "url2"),
            RemoteAlbum(3, "test3", "url3")
        ),
        total = 42,
        next = "next.com"
    )

    private val albumsResultStub = DomainResult.Success(
        data = listOf(
            Album(1, "test1", "url1"),
            Album(2, "test2", "url2"),
            Album(3, "test3", "url3")
        ),
        totalSize = 42,
        next = "next.com"
    )

    private val tracksResponseStub = RemoteResponse(
        data = listOf(
            RemoteTrack(1, "test1", 1, 1),
            RemoteTrack(2, "test2", 2, 1),
            RemoteTrack(3, "test3", 1, 2)
        ),
        total = 42,
        next = "next.com"
    )

    private val tracksResultStub = DomainResult.Success(
        data = listOf(
            Track(1, "test1", 1, 1),
            Track(2, "test2", 2, 1),
            Track(3, "test3", 1, 2)
        ),
        totalSize = 42,
        next = "next.com"
    )

    private val searchService = mock<SearchService>()
    private val artistRepository = ArtistRepositoryImpl(searchService)

    @Test
    fun searchForArtists_usingSearchService() = runBlockingTest {
        val query = "test"
        whenever(searchService.search(query)).thenReturn(searchResponseStub)

        artistRepository.searchForArtists(query)

        verify(searchService).search(query)
    }

    @Test
    fun mapSearchResponse_toResults() = runBlockingTest {
        val query = "test"
        whenever(searchService.search(query)).thenReturn(searchResponseStub)

        val actual = artistRepository.searchForArtists(query)

        assertThat(actual).isEqualTo(searchArtistsResultStub)
    }

    @Test
    fun getAlbums_usingSearchService() = runBlockingTest {
        val artistId = 1L
        whenever(searchService.getAlbums(artistId)).thenReturn(albumsResponseStub)

        artistRepository.getAlbums(artistId)

        verify(searchService).getAlbums(artistId)
    }

    @Test
    fun mapAlbumResponse_toAlbumResult() = runBlockingTest {
        val artistId = 1L
        whenever(searchService.getAlbums(artistId)).thenReturn(albumsResponseStub)

        val actual = artistRepository.getAlbums(artistId)

        assertThat(actual).isEqualTo(albumsResultStub)
    }

    @Test
    fun mapTrackResponse_toTrackResult() = runBlockingTest {
        val albumId = 1L
        whenever(searchService.getTracks(albumId)).thenReturn(tracksResponseStub)

        val actual = artistRepository.getTracks(albumId)

        assertThat(actual).isEqualTo(tracksResultStub)
    }
}