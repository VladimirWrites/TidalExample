package dev.vladimirj.tidal.search.ui.albums

import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist

fun Album.toAlbumUiModel(
    artist: Artist,
    onAlbumSelected: (Album) -> Unit
) = AlbumUiModel(
    this,
    artist,
    onAlbumSelected
)