package dev.vladimirj.tidal.search.domain.usecase

import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Recording
import javax.inject.Inject

class FilterAlbums @Inject constructor() {
    operator fun invoke(recordings: DomainResult.Success<*>): DomainResult.Success<Album> {
        return DomainResult.Success(
            data = recordings.data.map { it as Recording }.filter { it.recordType == "album" }.map {
                Album(
                    id = it.id,
                    title = it.title,
                    cover = it.cover
                )
            },
            totalSize = recordings.totalSize,
            next = recordings.next
        )
    }

}