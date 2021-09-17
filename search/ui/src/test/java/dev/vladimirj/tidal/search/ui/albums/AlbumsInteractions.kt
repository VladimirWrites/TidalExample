package dev.vladimirj.tidal.search.ui.albums

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId

object AlbumsInteractions {
    fun snackbar(): ViewInteraction = onView(withId(com.google.android.material.R.id.snackbar_text))
}