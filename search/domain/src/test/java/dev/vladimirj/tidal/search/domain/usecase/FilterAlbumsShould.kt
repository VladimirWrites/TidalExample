package dev.vladimirj.tidal.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import dev.vladimirj.tidal.search.domain.stubs.ALBUM_RESULT_STUB
import dev.vladimirj.tidal.search.domain.stubs.RECORDING_RESULT_STUB

import org.junit.Test

class FilterAlbumsShould {

    private val filterAlbums = FilterAlbums()

    @Test
    fun filterOutEverythingThatIsNotAlbum() {
        val expected = ALBUM_RESULT_STUB

        val actual = filterAlbums(RECORDING_RESULT_STUB)

        assertThat(actual).isEqualTo(expected)
    }
}