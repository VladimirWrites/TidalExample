package dev.vladimirj.tidal.search.domain.stubs

import dev.vladimirj.tidal.search.domain.DomainResult

val ALBUM_RESULT_STUB = DomainResult.Success(
    data = listOf(
        ALBUM
    ),
    totalSize = 42,
    next = "next.com"
)