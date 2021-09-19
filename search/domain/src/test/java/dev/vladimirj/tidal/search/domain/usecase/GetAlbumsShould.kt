package dev.vladimirj.tidal.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.stubs.RECORDING_RESULT_STUB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAlbumsShould {

    private val artistRepositoryMock = mock<ArtistRepository>()
    private val filterAlbums = FilterAlbums()
    private val getAlbums = GetAlbums(artistRepositoryMock, filterAlbums)

    @Test
    fun returnDataFromRepository() = runBlockingTest {
        val artistId = 1L
        val expected = filterAlbums(RECORDING_RESULT_STUB)
        whenever(artistRepositoryMock.getRecordings(artistId)).thenReturn(RECORDING_RESULT_STUB)

        val actual = getAlbums(artistId)

        assertThat(actual).isEqualTo(expected)
    }
}