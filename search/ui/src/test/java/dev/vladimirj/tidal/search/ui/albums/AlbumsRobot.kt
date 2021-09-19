package dev.vladimirj.tidal.search.ui.albums

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dev.vladimirj.tidal.search.ui.albums.AlbumsInteractions.noResultsView
import dev.vladimirj.tidal.search.ui.albums.AlbumsInteractions.snackbar

fun albumsScreen(block: AlbumsRobot.() -> Unit): AlbumsRobot {
    return AlbumsRobot().apply { block() }
}

class AlbumsRobot {

    fun checkSnackbarShown(text: String) {
        snackbar().check(matches(withText(text)))
    }

    fun checkNoResultsViewShown() {
        noResultsView().check(matches(isDisplayed()))
    }
}