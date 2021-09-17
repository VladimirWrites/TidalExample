package dev.vladimirj.tidal.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class GetAlbumsShould {

    private val artistRepositoryMock = mock<ArtistRepository>()
    private val getAlbums = GetAlbums(artistRepositoryMock)

    @Test
    fun returnDataFromRepository() = runBlockingTest {
        val expected = mock<DomainResult>()
        val artistId = 1L
        whenever(artistRepositoryMock.getAlbums(artistId)).thenReturn(expected)

        val actual = getAlbums(artistId)

        assertThat(actual).isEqualTo(expected)
    }
}