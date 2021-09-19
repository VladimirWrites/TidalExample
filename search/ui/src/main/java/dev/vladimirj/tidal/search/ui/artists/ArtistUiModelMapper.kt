package dev.vladimirj.tidal.search.ui.artists

import dev.vladimirj.tidal.search.domain.entity.Artist

fun List<Artist>.toArtistUiModels(onArtistSelected: (Artist) -> Unit) = this.map {
    it.toArtistUiModel(onArtistSelected)
}

private fun Artist.toArtistUiModel(
    onArtistSelected: (Artist) -> Unit
) = SearchResultsUiModel.ArtistUiModel(
    this,
    onArtistSelected
)
