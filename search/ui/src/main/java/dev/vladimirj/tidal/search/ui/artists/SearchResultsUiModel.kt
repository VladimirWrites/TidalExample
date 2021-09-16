package dev.vladimirj.tidal.search.ui.artists

import dev.vladimirj.tidal.search.domain.entity.Artist

sealed class SearchResultsUiModel {
    object HeaderUiModel : SearchResultsUiModel()
    class ArtistUiModel(
        val artist: Artist,
        val onClickListener: (artist: Artist) -> Unit
    ) : SearchResultsUiModel() {
        fun onClick() = onClickListener(artist)
    }
}