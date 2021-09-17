package dev.vladimirj.tidal.search.ui.album

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import dev.vladimirj.tidal.search.ui.albums.AlbumsInteractions.snackbar

fun albumDetailsScreen(block: AlbumDetailsRobot.() -> Unit): AlbumDetailsRobot {
    return AlbumDetailsRobot().apply { block() }
}

class AlbumDetailsRobot {

    fun checkSnackbarShown(text: String) {
        snackbar().check(matches(withText(text)))
    }
}