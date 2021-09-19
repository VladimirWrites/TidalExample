package dev.vladimirj.tidal.search.ui.artists

import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import dev.vladimirj.tidal.search.ui.artists.SearchInteractions.cancelButton
import dev.vladimirj.tidal.search.ui.artists.SearchInteractions.noResultsView
import dev.vladimirj.tidal.search.ui.artists.SearchInteractions.searchEditText
import dev.vladimirj.tidal.search.ui.artists.SearchInteractions.snackbar

fun searchScreen(block: SearchRobot.() -> Unit): SearchRobot {
    return SearchRobot().apply { block() }
}

class SearchRobot {

    fun enterQuery(query: String) {
        searchEditText().perform(typeText(query))
    }

    fun clickCancelButton() {
        cancelButton().perform(click())
    }

    fun checkSnackbarShown(text: String) {
        snackbar().check(matches(withText(text)))
    }

    fun checkNoResultsViewShown() {
        noResultsView().check(matches(isDisplayed()))
    }
}