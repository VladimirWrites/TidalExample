package dev.vladimirj.tidal.search.ui.album

import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.entity.Track

fun List<Track>.toAlbumDetailsModels(album: Album, artist: Artist): List<AlbumDetailsUiModel> {
    val albumDetailsUiModels = mutableListOf<AlbumDetailsUiModel>()

    albumDetailsUiModels.add(0, AlbumDetailsUiModel.HeaderUiModel(
        album, artist
    ))

    val groupedByDiskNumber = this.groupBy { it.diskNumber }

    if(groupedByDiskNumber.keys.size == 1) {
        this.sortedBy { it.trackPosition }.forEach {
            albumDetailsUiModels.add(it.toTrackUiModel(artist))
        }
    } else {
        groupedByDiskNumber.keys.toList().sorted().forEach { volumeNumber ->
            albumDetailsUiModels.add(AlbumDetailsUiModel.VolumeUiModel(
                volumeNumber
            ))
            groupedByDiskNumber[volumeNumber]!!.sortedBy { it.trackPosition }.forEach {
                albumDetailsUiModels.add(it.toTrackUiModel(artist))
            }
        }
    }

    return albumDetailsUiModels
}

private fun Track.toTrackUiModel(
    artist: Artist
) = AlbumDetailsUiModel.TrackUiModel(
    artist,
    this
)

