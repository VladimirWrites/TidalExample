package dev.vladimirj.tidal.search.ui.artists

import androidx.recyclerview.widget.DiffUtil
import dev.vladimirj.tidal.search.ui.R
import dev.vladimirj.tidal.search.ui.databinding.DataBindingRecyclerAdapter

class SearchResultsAdapter : DataBindingRecyclerAdapter<SearchResultsUiModel>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<SearchResultsUiModel>() {

        override fun areItemsTheSame(
            oldItem: SearchResultsUiModel,
            newItem: SearchResultsUiModel
        ): Boolean {
            return if (oldItem is SearchResultsUiModel.ArtistUiModel && newItem is SearchResultsUiModel.ArtistUiModel) {
                oldItem.artist.id == newItem.artist.id
            } else oldItem is SearchResultsUiModel.HeaderUiModel && newItem is SearchResultsUiModel.HeaderUiModel
        }

        override fun areContentsTheSame(
            oldItem: SearchResultsUiModel,
            newItem: SearchResultsUiModel
        ): Boolean {
            return if (oldItem is SearchResultsUiModel.ArtistUiModel && newItem is SearchResultsUiModel.ArtistUiModel) {
                oldItem.artist == newItem.artist
            } else oldItem is SearchResultsUiModel.HeaderUiModel && newItem is SearchResultsUiModel.HeaderUiModel
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is SearchResultsUiModel.ArtistUiModel -> R.layout.item_artist
        is SearchResultsUiModel.HeaderUiModel -> R.layout.item_header_artist
    }
}