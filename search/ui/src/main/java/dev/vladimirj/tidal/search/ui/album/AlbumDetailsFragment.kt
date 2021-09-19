package dev.vladimirj.tidal.search.ui.album

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.vladimirj.tidal.base.ui.observe
import dev.vladimirj.tidal.base.ui.observeEvent
import dev.vladimirj.tidal.search.ui.R
import dev.vladimirj.tidal.search.ui.albums.ParcelableAlbum
import dev.vladimirj.tidal.search.ui.albums.toAlbum
import dev.vladimirj.tidal.search.ui.artists.ParcelableArtist
import dev.vladimirj.tidal.search.ui.artists.toArtist
import dev.vladimirj.tidal.search.ui.databinding.FragmentAlbumDetailsBinding

private const val ARG_ARTIST = "artist"
private const val ARG_ALBUM = "album"

@AndroidEntryPoint
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {

    private val viewModel by viewModels<AlbumDetailsViewModel>()
    private lateinit var binding: FragmentAlbumDetailsBinding

    private val adapter = AlbumDetailsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumDetailsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeEvent(viewModel.uiEvents) {
            when (it) {
                is AlbumDetailsViewModel.UiEvent.ShowError -> Snackbar.make(
                    binding.root,
                    it.message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        observe(viewModel.albumResults) {
            adapter.submitList(it)
        }

        setupToolbar()

        binding.recyclerAlbums.adapter = adapter
        binding.recyclerAlbums.itemAnimator = null

        viewModel.loadAlbum(
            requireArguments().getParcelable<ParcelableAlbum>(ARG_ALBUM)!!.toAlbum(),
            requireArguments().getParcelable<ParcelableArtist>(ARG_ARTIST)!!.toArtist()
        )
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        const val TAG = "AlbumsFragment"

        fun newInstance(artist: ParcelableArtist, album: ParcelableAlbum) = AlbumDetailsFragment().apply {
            arguments = bundleOf(
                ARG_ARTIST to artist,
                ARG_ALBUM to album
            )
        }
    }
}
