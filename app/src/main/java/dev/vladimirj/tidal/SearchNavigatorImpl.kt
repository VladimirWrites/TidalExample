package dev.vladimirj.tidal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dev.vladimirj.tidal.search.ui.artists.SearchFragment
import dev.vladimirj.tidal.search.ui.SearchNavigator
import dev.vladimirj.tidal.search.ui.album.AlbumDetailsFragment
import dev.vladimirj.tidal.search.ui.albums.AlbumsFragment
import dev.vladimirj.tidal.search.ui.albums.ParcelableAlbum
import dev.vladimirj.tidal.search.ui.artists.ParcelableArtist

class SearchNavigatorImpl: SearchNavigator {

    override fun goToSearch(activity: FragmentActivity) {
        navigateToFragment(activity, SearchFragment.newInstance(), SearchFragment.TAG)
    }

    override fun goToAlbums(activity: FragmentActivity, artist: ParcelableArtist) {
        navigateToFragment(activity, AlbumsFragment.newInstance(artist), AlbumsFragment.TAG, true)
    }

    override fun goToAlbumDetails(activity: FragmentActivity, artist: ParcelableArtist, album: ParcelableAlbum) {
        navigateToFragment(activity, AlbumDetailsFragment.newInstance(artist, album), AlbumDetailsFragment.TAG, true)
    }

    private fun navigateToFragment(activity: FragmentActivity, fragment: Fragment, tag: String, addToBackStack: Boolean = false) {
        val ft = activity.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment, tag)
        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.commit()
    }
}