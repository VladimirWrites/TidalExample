package dev.vladimirj.tidal.search.ui.album

import android.os.Build
import androidx.core.os.bundleOf
import com.nhaarman.mockitokotlin2.*
import dagger.hilt.android.testing.*
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.usecase.GetAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetMoreAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetTracks
import dev.vladimirj.tidal.search.ui.SearchNavigator
import dev.vladimirj.tidal.search.ui.albums.toParcelableAlbum
import dev.vladimirj.tidal.search.ui.artists.toParcelableArtist
import dev.vladimirj.tidal.search.ui.stubs.ALBUM_1
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
class AlbumDetailsFragmentShould {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val getTracks = mock<GetTracks>()

    @BindValue
    val navigator = mock<SearchNavigator>()

    @BindValue
    val coroutineDispatcherProvider = CoroutineDispatcherProvider(
        main = Dispatchers.Unconfined, io = Dispatchers.Unconfined
    )

    @Test
    fun showError_returnedByGetAlbumsUsecase() = runBlockingTest {
        val error = DomainResult.Error("message")
        whenever(getTracks(ARTIST_1.id)).thenReturn(error)

        launchFragmentInHiltContainer<AlbumDetailsFragment>(
            bundleOf(
                "artist" to ARTIST_1.toParcelableArtist(),
                "album" to ALBUM_1.toParcelableAlbum()
            )
        )

        albumDetailsScreen {
            checkSnackbarShown(error.message)
        }
    }
}
