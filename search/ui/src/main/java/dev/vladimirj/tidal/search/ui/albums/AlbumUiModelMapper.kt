package dev.vladimirj.tidal.search.ui.albums

import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist

fun List<Album>.toAlbumUiModels(artist: Artist, onAlbumSelected: (Album) -> Unit) = this.map {
    it.toAlbumUiModel(artist, onAlbumSelected)
}

private fun Album.toAlbumUiModel(
    artist: Artist,
    onAlbumSelected: (Album) -> Unit
) = AlbumUiModel(
    this,
    artist,
    onAlbumSelected
)