package dev.vladimirj.tidal.search.ui.album

import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.entity.Track

sealed class AlbumDetailsUiModel {
    data class HeaderUiModel(
        val album: Album,
        val artist: Artist
    ) : AlbumDetailsUiModel()

    data class VolumeUiModel(
        val volumeNumber: Int
    ) : AlbumDetailsUiModel()

    data class TrackUiModel(
        val artist: Artist,
        val track: Track
    ) : AlbumDetailsUiModel()
}