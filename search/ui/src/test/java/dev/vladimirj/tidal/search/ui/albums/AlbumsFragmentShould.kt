package dev.vladimirj.tidal.search.ui.albums

import android.os.Build
import androidx.core.os.bundleOf
import com.nhaarman.mockitokotlin2.*
import dagger.hilt.android.testing.*
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.usecase.GetAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetMoreAlbums
import dev.vladimirj.tidal.search.ui.SearchNavigator
import dev.vladimirj.tidal.search.ui.artists.toParcelableArtist
import dev.vladimirj.tidal.search.ui.stubs.ARTIST_1
import dev.vladimirj.tidal.search.ui.utils.launchFragmentInHiltContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(sdk = [Build.VERSION_CODES.P], application = HiltTestApplication::class)
class AlbumsFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val getMoreAlbums = mock<GetMoreAlbums>()

    @BindValue
    val getAlbums = mock<GetAlbums>()

    @BindValue
    val navigator = mock<SearchNavigator>()

    @BindValue
    val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = Dispatchers.Unconfined, io = Dispatchers.Unconfined
    )

    @Test
    fun showError_whenReturnedByGetAlbumsUsecase() = runBlockingTest {
        val error = DomainResult.Error("message")
        whenever(getAlbums(ARTIST_1.id)).thenReturn(error)

        launchFragmentInHiltContainer<AlbumsFragment>(
            bundleOf(
                "artist" to ARTIST_1.toParcelableArtist()
            )
        )

        albumsScreen {
            checkSnackbarShown(error.message)
        }
    }

    @Test
    fun showEmptyState_whenNoResults() = runBlockingTest {
        whenever(getAlbums(ARTIST_1.id)).thenReturn(DomainResult.Success<Album>(emptyList(), 0, null))

        launchFragmentInHiltContainer<AlbumsFragment>(
            bundleOf(
                "artist" to ARTIST_1.toParcelableArtist()
            )
        )

        albumsScreen {
            checkNoResultsViewShown()
        }
    }

    // TODO: Add tests to verify that albums are shown when returned by GetAlbums use-case
}
