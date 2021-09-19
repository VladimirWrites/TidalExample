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
class GetMoreAlbumsShould {

    private val artistRepositoryMock = mock<ArtistRepository>()
    private val getMoreAlbums = GetMoreAlbums(artistRepositoryMock)

    @Test
    fun returnDataFromRepository() = runBlockingTest {
        val expected = mock<DomainResult>()
        val url = "https://test.com"
        whenever(artistRepositoryMock.getMoreAlbums(url)).thenReturn(expected)

        val actual = getMoreAlbums(url)

        assertThat(actual).isEqualTo(expected)
    }
}