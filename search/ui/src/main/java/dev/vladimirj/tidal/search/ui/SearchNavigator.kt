package dev.vladimirj.tidal.search.ui

import androidx.fragment.app.FragmentActivity
import dev.vladimirj.tidal.search.ui.albums.ParcelableAlbum
import dev.vladimirj.tidal.search.ui.artists.ParcelableArtist

interface SearchNavigator {
    fun goToSearch(activity: FragmentActivity)
    fun goToAlbums(activity: FragmentActivity, artist: ParcelableArtist)
    fun goToAlbumDetails(activity: FragmentActivity, artist: ParcelableArtist, album: ParcelableAlbum)
}