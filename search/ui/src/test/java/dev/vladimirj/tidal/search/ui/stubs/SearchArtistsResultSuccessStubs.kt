package dev.vladimirj.tidal.search.ui.stubs

import dev.vladimirj.tidal.search.domain.DomainResult

val SEARCH_ARTISTS_RESULT_SUCCESS_1 = DomainResult.Success(
    data = listOf(
        ARTIST_1,
        ARTIST_2,
        ARTIST_3
    ),
    totalSize = 42,
    next = "next.com/1"
)

val SEARCH_ARTISTS_RESULT_SUCCESS_2 = DomainResult.Success(
    data = listOf(
        ARTIST_4,
        ARTIST_5
    ),
    totalSize = 42,
    next = null
)