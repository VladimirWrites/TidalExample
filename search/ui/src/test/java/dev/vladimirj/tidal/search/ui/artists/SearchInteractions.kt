package dev.vladimirj.tidal.search.ui.artists

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import dev.vladimirj.tidal.search.ui.R

object SearchInteractions {

    fun searchEditText(): ViewInteraction = onView(withId(R.id.text_search_field))
    fun cancelButton(): ViewInteraction = onView(withId(R.id.button_cancel))
    fun snackbar(): ViewInteraction = onView(withId(com.google.android.material.R.id.snackbar_text))
}