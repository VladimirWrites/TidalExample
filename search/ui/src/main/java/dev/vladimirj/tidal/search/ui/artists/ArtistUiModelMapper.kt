package dev.vladimirj.tidal.search.ui.artists

import dev.vladimirj.tidal.search.domain.entity.Artist

fun Artist.toArtistUiModel(
    onArtistSelected: (Artist) -> Unit
) = SearchResultsUiModel.ArtistUiModel(
    this,
    onArtistSelected
)