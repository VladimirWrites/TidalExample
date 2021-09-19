package dev.vladimirj.tidal.search.ui.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.usecase.GetTracks
import dev.vladimirj.tidal.search.ui.stubs.ALBUM_1
import dev.vladimirj.tidal.search.ui.stubs.ALBUM_DETAILS_RESULT_SUCCESS_WITHOUT_VOLUMES
import dev.vladimirj.tidal.search.ui.stubs.ALBUM_DETAILS_RESULT_SUCCESS_WITH_VOLUMES
import dev.vladimirj.tidal.search.ui.stubs.ARTIST_1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumDetailsViewModelShould {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getTracks = mock<GetTracks>()
    private val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = Dispatchers.Unconfined, io = Dispatchers.Unconfined
    )

    private val viewModel = AlbumDetailsViewModel(getTracks, coroutineDispatcherProvider)

    @Test
    fun presentList_withVolumes_providedByGetAlbumDetails() = runBlockingTest {
        whenever(getTracks(ALBUM_1.id)).thenReturn(ALBUM_DETAILS_RESULT_SUCCESS_WITH_VOLUMES)

        viewModel.loadAlbum(ALBUM_1, ARTIST_1)

        assertThat(viewModel.albumResults.value!!.size).isEqualTo(ALBUM_DETAILS_RESULT_SUCCESS_WITH_VOLUMES.data.size + 3)
    }

    @Test
    fun presentList_withoutVolumes_providedByGetAlbumDetails() = runBlockingTest {
        whenever(getTracks(ALBUM_1.id)).thenReturn(ALBUM_DETAILS_RESULT_SUCCESS_WITHOUT_VOLUMES)

        viewModel.loadAlbum(ALBUM_1, ARTIST_1)

        assertThat(viewModel.albumResults.value!!.size).isEqualTo(ALBUM_DETAILS_RESULT_SUCCESS_WITHOUT_VOLUMES.data.size + 1)
    }

    // TODO: Additional test should be added to check if correct data is correctly mapped by ViewModel
}