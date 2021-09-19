package dev.vladimirj.tidal.search.ui.albums

import android.os.Parcelable
import dev.vladimirj.tidal.search.domain.entity.Album
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableAlbum(
    val id: Long,
    val title: String,
    val cover: String
) : Parcelable

fun ParcelableAlbum.toAlbum() = Album(
    id = this.id,
    title = this.title,
    cover = this.cover
)

fun Album.toParcelableAlbum() = ParcelableAlbum(
    id = this.id,
    title = this.title,
    cover = this.cover
)