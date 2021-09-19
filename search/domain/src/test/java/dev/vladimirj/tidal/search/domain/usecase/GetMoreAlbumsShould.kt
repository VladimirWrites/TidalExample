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
class GetMoreAlbumsShould {

    private val artistRepositoryMock = mock<ArtistRepository>()
    private val filterAlbums = FilterAlbums()
    private val getMoreAlbums = GetMoreAlbums(artistRepositoryMock, filterAlbums)

    @Test
    fun returnFilteredDataFromRepository() = runBlockingTest {
        val url = "https://test.com"
        val expected = filterAlbums(RECORDING_RESULT_STUB)
        whenever(artistRepositoryMock.getMoreRecordings(url)).thenReturn(RECORDING_RESULT_STUB)

        val actual = getMoreAlbums(url)

        assertThat(actual).isEqualTo(expected)
    }
}