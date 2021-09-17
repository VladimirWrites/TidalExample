package dev.vladimirj.tidal.search.ui.artists

import android.os.Parcelable
import dev.vladimirj.tidal.search.domain.entity.Artist
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableArtist(
    val id: Long,
    val name: String,
    val picture: String
): Parcelable


fun ParcelableArtist.toArtist() = Artist(
    id = this.id,
    name = this.name,
    picture = this.picture
)

fun Artist.toParcelableArtist() = ParcelableArtist(
    id = this.id,
    name = this.name,
    picture = this.picture
)