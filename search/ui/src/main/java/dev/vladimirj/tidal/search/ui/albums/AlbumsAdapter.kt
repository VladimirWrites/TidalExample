package dev.vladimirj.tidal.search.ui.albums

import androidx.recyclerview.widget.DiffUtil
import dev.vladimirj.tidal.search.ui.R
import dev.vladimirj.tidal.search.ui.databinding.DataBindingRecyclerAdapter

class AlbumsAdapter : DataBindingRecyclerAdapter<AlbumUiModel>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<AlbumUiModel>() {

        override fun areItemsTheSame(
            oldItem: AlbumUiModel,
            newItem: AlbumUiModel
        ): Boolean {
            return oldItem.album.id == newItem.album.id
        }

        override fun areContentsTheSame(
            oldItem: AlbumUiModel,
            newItem: AlbumUiModel
        ): Boolean {
            return oldItem.album == newItem.album
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_album
}