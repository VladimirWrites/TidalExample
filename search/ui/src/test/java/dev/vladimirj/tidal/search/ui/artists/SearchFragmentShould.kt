package dev.vladimirj.tidal.search.ui.artists

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.usecase.LoadMoreArtists
import dev.vladimirj.tidal.search.domain.usecase.SearchArtists
import dev.vladimirj.tidal.search.ui.SearchNavigator
import dev.vladimirj.tidal.search.ui.stubs.SEARCH_ARTISTS_RESULT_SUCCESS_1
import dev.vladimirj.tidal.search.ui.utils.TestCoroutineRule
import dev.vladimirj.tidal.search.ui.utils.launchFragmentInHiltContainer
import dev.vladimirj.tidal.search.ui.utils.testDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(sdk = [Build.VERSION_CODES.P], application = HiltTestApplication::class)
class SearchFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @BindValue
    val searchArtists = mock<SearchArtists>()

    @BindValue
    val loadMoreArtists = mock<LoadMoreArtists>()

    @BindValue
    val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = coroutineRule.testDispatcher, io = coroutineRule.testDispatcher
    )

    @BindValue
    val navigator = mock<SearchNavigator>()

    @Test
    fun querySearchArtistsUsecase() = coroutineRule.runBlockingTest {
        launchFragmentInHiltContainer<SearchFragment>()

        searchScreen {
            enterQuery("test")
        }

        testDispatcher.advanceUntilIdle()
        verify(searchArtists).invoke("test")
    }

    @Test
    fun showError_whenReturnedBySearchArtistsUsecase() = coroutineRule.runBlockingTest {
        val error = DomainResult.Error("message")
        whenever(searchArtists("test")).thenReturn(error)
        launchFragmentInHiltContainer<SearchFragment>()

        searchScreen {
            enterQuery("test")
            testDispatcher.advanceUntilIdle()
            checkSnackbarShown(error.message)
        }
    }

    @Test
    fun showNoResultsMessage_whenNoResults() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(DomainResult.Success<Artist>(emptyList(), 0, null))
        launchFragmentInHiltContainer<SearchFragment>()

        searchScreen {
            enterQuery("test")
            testDispatcher.advanceUntilIdle()
            checkNoResultsViewShown()
        }
    }

    @Test
    fun removeResults_whenCancelButtonIsPressed() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(SEARCH_ARTISTS_RESULT_SUCCESS_1)
        launchFragmentInHiltContainer<SearchFragment>()

        searchScreen {
            enterQuery("test")
            testDispatcher.advanceUntilIdle()
            clickCancelButton()
            testDispatcher.advanceUntilIdle()
            checkSearchResultsEmpty()
        }
    }

    @Test
    fun hideNoResultsMessage_whenCancelButtonIsPressed() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(SEARCH_ARTISTS_RESULT_SUCCESS_1)
        launchFragmentInHiltContainer<SearchFragment>()

        searchScreen {
            enterQuery("test")
            testDispatcher.advanceUntilIdle()
            clickCancelButton()
            testDispatcher.advanceUntilIdle()
            checkNoResultsViewHidden()
        }
    }

    // TODO: Add tests to verify that artists are shown when returned by SearchArtists use-case
}
