package dev.vladimirj.tidal.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadMoreArtistsShould {

    private val artistRepositoryMock = mock<ArtistRepository>()
    private val loadMoreArtists = LoadMoreArtists(artistRepositoryMock)

    @Test
    fun returnDataFromRepository() = runBlockingTest {
        val expected = mock<DomainResult>()
        val url = "https://test.com"
        whenever(artistRepositoryMock.getMoreSearchResults(url)).thenReturn(expected)

        val actual = loadMoreArtists(url)

        assertThat(actual).isEqualTo(expected)
    }
}