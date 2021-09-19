package dev.vladimirj.tidal.search.domain.stubs

import dev.vladimirj.tidal.search.domain.DomainResult

val RECORDING_RESULT_STUB = DomainResult.Success(
    data = listOf(
        RECORDING_ALBUM,
        RECORDING_SINGLE,
        RECORDING_EP
    ),
    totalSize = 42,
    next = "next.com"
)
