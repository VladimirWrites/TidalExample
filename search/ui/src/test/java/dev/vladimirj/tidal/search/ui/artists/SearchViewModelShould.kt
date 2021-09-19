package dev.vladimirj.tidal.search.ui.artists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.usecase.LoadMoreArtists
import dev.vladimirj.tidal.search.domain.usecase.SearchArtists
import dev.vladimirj.tidal.search.ui.stubs.SEARCH_ARTISTS_RESULT_SUCCESS_1
import dev.vladimirj.tidal.search.ui.stubs.SEARCH_ARTISTS_RESULT_SUCCESS_2
import dev.vladimirj.tidal.search.ui.utils.TestCoroutineRule
import dev.vladimirj.tidal.search.ui.utils.testDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelShould {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val searchArtists = mock<SearchArtists>()
    private val loadMoreArtists = mock<LoadMoreArtists>()
    private val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = coroutineRule.testDispatcher, io = coroutineRule.testDispatcher
    )

    private val viewModel = SearchViewModel(searchArtists, loadMoreArtists, coroutineDispatcherProvider)

    @Test
    fun presentEmptyList_whenSearchIsCanceled() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(SEARCH_ARTISTS_RESULT_SUCCESS_1)
        viewModel.searchQuery.set("test")
        testDispatcher.advanceUntilIdle()
        assertThat(viewModel.searchResults.value!!.size).isEqualTo(SEARCH_ARTISTS_RESULT_SUCCESS_1.data.size + 1)

        viewModel.cancelSearch()

        testDispatcher.advanceUntilIdle()
        assertThat(viewModel.searchResults.value!!).isEmpty()
    }

    @Test
    fun presentList_providedBySearchArtistsAndHeader() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(SEARCH_ARTISTS_RESULT_SUCCESS_1)

        viewModel.searchQuery.set("test")
        testDispatcher.advanceUntilIdle()

        assertThat(viewModel.searchResults.value!!.size).isEqualTo(SEARCH_ARTISTS_RESULT_SUCCESS_1.data.size + 1)
    }

    @Test
    fun addArtistsToExistingList_whenLoadMoreIsCalled() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(SEARCH_ARTISTS_RESULT_SUCCESS_1)
        whenever(loadMoreArtists(SEARCH_ARTISTS_RESULT_SUCCESS_1.next!!)).thenReturn(SEARCH_ARTISTS_RESULT_SUCCESS_2)
        viewModel.searchQuery.set("test")
        testDispatcher.advanceUntilIdle()
        assertThat(viewModel.searchResults.value!!.size).isEqualTo(SEARCH_ARTISTS_RESULT_SUCCESS_1.data.size + 1)

        viewModel.loadMore()

        testDispatcher.advanceUntilIdle()
        assertThat(viewModel.searchResults.value!!.size).isEqualTo(SEARCH_ARTISTS_RESULT_SUCCESS_1.data.size + SEARCH_ARTISTS_RESULT_SUCCESS_2.data.size + 1)
    }

    @Test
    fun showEmptyState_whenNoSearchResults() = coroutineRule.runBlockingTest {
        val searchQuery = "search_query"
        whenever(searchArtists(searchQuery)).thenReturn(DomainResult.Success<Artist>(emptyList(), 0, null))
        assertThat(viewModel.isNoResultsVisible.get()).isFalse()

        viewModel.searchQuery.set(searchQuery)
        testDispatcher.advanceUntilIdle()

        assertThat(viewModel.isNoResultsVisible.get()).isTrue()
    }

    // TODO: Additional test should be added to check if correct data is correctly mapped by ViewModel
}