package dev.vladimirj.tidal.search.ui.album

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import dev.vladimirj.tidal.search.ui.R

object AlbumDetailsInteractions {
    fun snackbar(): ViewInteraction = onView(withId(com.google.android.material.R.id.snackbar_text))
}