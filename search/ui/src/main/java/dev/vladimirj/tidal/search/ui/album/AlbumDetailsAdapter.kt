package dev.vladimirj.tidal.search.ui.album

import androidx.recyclerview.widget.DiffUtil
import dev.vladimirj.tidal.search.ui.R
import dev.vladimirj.tidal.search.ui.databinding.DataBindingRecyclerAdapter

class AlbumDetailsAdapter : DataBindingRecyclerAdapter<AlbumDetailsUiModel>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<AlbumDetailsUiModel>() {

        override fun areItemsTheSame(
            oldItem: AlbumDetailsUiModel,
            newItem: AlbumDetailsUiModel
        ): Boolean {
            return if (oldItem is AlbumDetailsUiModel.TrackUiModel && newItem is AlbumDetailsUiModel.TrackUiModel) {
                oldItem.track.id == newItem.track.id
            } else if (oldItem is AlbumDetailsUiModel.HeaderUiModel && newItem is AlbumDetailsUiModel.HeaderUiModel) {
                oldItem.album.id == newItem.album.id
            } else if (oldItem is AlbumDetailsUiModel.VolumeUiModel && newItem is AlbumDetailsUiModel.VolumeUiModel) {
                oldItem.volumeNumber == newItem.volumeNumber
            } else {
                false
            }
        }

        override fun areContentsTheSame(
            oldItem: AlbumDetailsUiModel,
            newItem: AlbumDetailsUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is AlbumDetailsUiModel.TrackUiModel -> R.layout.item_track
        is AlbumDetailsUiModel.HeaderUiModel -> R.layout.item_header_album
        is AlbumDetailsUiModel.VolumeUiModel -> R.layout.item_header_volume
    }
}