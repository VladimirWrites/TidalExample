package dev.vladimirj.tidal.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.domain.SearchArtistsResult
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchArtistsShould {

    private val artistRepositoryMock = mock<ArtistRepository>()
    private val searchArtists = SearchArtists(artistRepositoryMock)

    @Test
    fun returnDataFromRepository() = runBlockingTest {
        val expected = mock<SearchArtistsResult>()
        val query = "test"
        whenever(artistRepositoryMock.searchForArtists(query)).thenReturn(expected)

        val actual = searchArtists(query)

        assertThat(actual).isEqualTo(expected)
    }
}