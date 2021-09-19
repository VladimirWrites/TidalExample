package dev.vladimirj.tidal.search.ui.artists

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import dagger.hilt.android.testing.*
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.usecase.LoadMoreArtists
import dev.vladimirj.tidal.search.domain.usecase.SearchArtists
import dev.vladimirj.tidal.search.ui.SearchNavigator
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
    fun showError_returnedBySearchArtistsUsecase() = coroutineRule.runBlockingTest {
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
    fun showEmptyState_whenNoResults() = coroutineRule.runBlockingTest {
        whenever(searchArtists("test")).thenReturn(DomainResult.Success<Artist>(emptyList(), 0, null))
        launchFragmentInHiltContainer<SearchFragment>()

        searchScreen {
            enterQuery("test")
            testDispatcher.advanceUntilIdle()
            checkNoResultsViewShown()
        }
    }
}
