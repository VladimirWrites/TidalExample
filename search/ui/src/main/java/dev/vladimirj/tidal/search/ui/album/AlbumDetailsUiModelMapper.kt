package dev.vladimirj.tidal.search.ui.album

import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.entity.Track

fun Track.toTrackUiModel(
    artist: Artist
) = AlbumDetailsUiModel.TrackUiModel(
    artist,
    this
)

