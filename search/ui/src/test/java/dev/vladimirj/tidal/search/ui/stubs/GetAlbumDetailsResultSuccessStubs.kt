package dev.vladimirj.tidal.search.ui.stubs

import dev.vladimirj.tidal.search.domain.DomainResult

val ALBUM_DETAILS_RESULT_SUCCESS_WITH_VOLUMES = DomainResult.Success(
    data = listOf(
        TRACK_1,
        TRACK_2,
        TRACK_3,
        TRACK_4,
        TRACK_5,
        TRACK_6,
        TRACK_7
    ),
    totalSize = 42,
    next = "next.com/1"
)

val ALBUM_DETAILS_RESULT_SUCCESS_WITHOUT_VOLUMES = DomainResult.Success(
    data = listOf(
        TRACK_1,
        TRACK_2,
        TRACK_3,
    ),
    totalSize = 42,
    next = "next.com/1"
)
