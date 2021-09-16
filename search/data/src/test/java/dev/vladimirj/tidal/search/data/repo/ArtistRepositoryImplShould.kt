package dev.vladimirj.tidal.search.data.repo

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.data.SearchService
import dev.vladimirj.tidal.search.data.entity.RemoteArtist
import dev.vladimirj.tidal.search.data.entity.SearchResponse
import dev.vladimirj.tidal.search.domain.SearchArtistsResult
import dev.vladimirj.tidal.search.domain.entity.Artist
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class ArtistRepositoryImplShould {

    private val searchResponseStub = SearchResponse(
        data = listOf(
            RemoteArtist(1, "test1", "url1"),
            RemoteArtist(2, "test2", "url2"),
            RemoteArtist(3, "test3", "url3")
        ),
        total = 42,
        next = "next.com"
    )

    private val searchArtistsResultStub = SearchArtistsResult.Success(
        artists = listOf(
            Artist(1, "test1", "url1"),
            Artist(2, "test2", "url2"),
            Artist(3, "test3", "url3")
        ),
        totalSize = 42,
        next = "next.com"
    )

    private val searchService = mock<SearchService>()
    private val artistRepository = ArtistRepositoryImpl(searchService)

    @Test
    fun searchForArtists_usingSearchService() = runBlockingTest{
        val query = "test"
        whenever(searchService.search(query)).thenReturn(searchResponseStub)

        artistRepository.searchForArtists(query)

        verify(searchService).search(query)
    }

    @Test
    fun mapSearchResponse_toSearchArtistsResult() = runBlockingTest{
        val query = "test"
        whenever(searchService.search(query)).thenReturn(searchResponseStub)

        val actual = artistRepository.searchForArtists(query)

        assertThat(actual).isEqualTo(searchArtistsResultStub)
    }
}