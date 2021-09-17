package dev.vladimirj.tidal.search.ui.stubs

import dev.vladimirj.tidal.search.domain.DomainResult

val ALBUMS_RESULT_SUCCESS_1 = DomainResult.Success(
    data = listOf(
        ALBUM_1,
        ALBUM_2,
        ALBUM_3
    ),
    totalSize = 42,
    next = "next.com/1"
)

val ALBUMS_RESULT_SUCCESS_2 = DomainResult.Success(
    data = listOf(
        ALBUM_4,
        ALBUM_5
    ),
    totalSize = 42,
    next = null
)