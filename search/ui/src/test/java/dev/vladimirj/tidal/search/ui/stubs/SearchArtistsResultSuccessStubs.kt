package dev.vladimirj.tidal.search.ui.stubs

import dev.vladimirj.tidal.search.domain.SearchArtistsResult

val SEARCH_ARTISTS_RESULT_SUCCESS_1 = SearchArtistsResult.Success(
    artists = listOf(
        ARTIST_1,
        ARTIST_2,
        ARTIST_3
    ),
    totalSize = 42,
    next = "next.com/1"
)

val SEARCH_ARTISTS_RESULT_SUCCESS_2 = SearchArtistsResult.Success(
    artists = listOf(
        ARTIST_4,
        ARTIST_5
    ),
    totalSize = 42,
    next = null
)