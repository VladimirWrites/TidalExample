package dev.vladimirj.tidal.search.ui.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.usecase.GetAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetMoreAlbums
import dev.vladimirj.tidal.search.ui.stubs.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsViewModelShould {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getAlbums = mock<GetAlbums>()
    private val getMoreAlbums = mock<GetMoreAlbums>()
    private val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = Dispatchers.Unconfined, io = Dispatchers.Unconfined
    )

    private val viewModel = AlbumsViewModel(getAlbums, getMoreAlbums, coroutineDispatcherProvider)

    @Test
    fun presentList_providedByGetAlbums() = runBlockingTest {
        whenever(getAlbums(ARTIST_1.id)).thenReturn(ALBUMS_RESULT_SUCCESS_1)

        viewModel.loadAlbums(ARTIST_1)

        assertThat(viewModel.albumResults.value!!.size).isEqualTo(ALBUMS_RESULT_SUCCESS_1.data.size)
    }

    @Test
    fun addArtistsToExistingList_whenLoadMoreIsCalled() = runBlockingTest {
        whenever(getAlbums(ARTIST_1.id)).thenReturn(ALBUMS_RESULT_SUCCESS_1)
        whenever(getMoreAlbums(ALBUMS_RESULT_SUCCESS_1.next!!)).thenReturn(ALBUMS_RESULT_SUCCESS_2)
        viewModel.loadAlbums(ARTIST_1)

        assertThat(viewModel.albumResults.value!!.size).isEqualTo(ALBUMS_RESULT_SUCCESS_1.data.size)

        viewModel.loadMore()

        assertThat(viewModel.albumResults.value!!.size).isEqualTo(ALBUMS_RESULT_SUCCESS_1.data.size + ALBUMS_RESULT_SUCCESS_2.data.size)
    }
}