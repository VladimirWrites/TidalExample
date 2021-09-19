package dev.vladimirj.tidal.search.ui.albums

import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist

class AlbumUiModel(
    val album: Album,
    val artist: Artist,
    val onClickListener: (artist: Album) -> Unit
) {
    fun onClick() = onClickListener(album)
}