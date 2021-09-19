package dev.vladimirj.tidal.search.ui.albums

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.vladimirj.tidal.base.ui.addOnScrolledToBottom
import dev.vladimirj.tidal.base.ui.observe
import dev.vladimirj.tidal.base.ui.observeEvent
import dev.vladimirj.tidal.search.ui.R
import dev.vladimirj.tidal.search.ui.SearchNavigator
import dev.vladimirj.tidal.search.ui.albums.AlbumsViewModel.UiEvent.GoToTracks
import dev.vladimirj.tidal.search.ui.albums.AlbumsViewModel.UiEvent.ShowError
import dev.vladimirj.tidal.search.ui.artists.ParcelableArtist
import dev.vladimirj.tidal.search.ui.artists.toArtist
import dev.vladimirj.tidal.search.ui.artists.toParcelableArtist
import dev.vladimirj.tidal.search.ui.databinding.FragmentAlbumsBinding
import javax.inject.Inject

private const val ARG_ARTIST = "artist"

@AndroidEntryPoint
class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    private val viewModel by viewModels<AlbumsViewModel>()
    private lateinit var binding: FragmentAlbumsBinding

    private val adapter = AlbumsAdapter()

    @Inject
    lateinit var navigator: SearchNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeEvent(viewModel.uiEvents) {
            when (it) {
                is GoToTracks -> navigator.goToAlbumDetails(
                    requireActivity(),
                    it.artist.toParcelableArtist(),
                    it.album.toParcelableAlbum()
                )
                is ShowError -> Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
            }
        }
        observe(viewModel.albumResults) {
            adapter.submitList(it)
        }

        setupToolbar()

        binding.recyclerAlbums.adapter = adapter
        binding.recyclerAlbums.itemAnimator = null

        binding.recyclerAlbums.addOnScrolledToBottom {
            viewModel.loadMore()
        }

        if (viewModel.albumResults.value.isNullOrEmpty()) {
            viewModel.loadAlbums(
                requireArguments().getParcelable<ParcelableArtist>(ARG_ARTIST)!!.toArtist()
            )
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            title = context.getString(R.string.albums)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        const val TAG = "AlbumsFragment"

        fun newInstance(artist: ParcelableArtist) = AlbumsFragment().apply {
            arguments = bundleOf(
                ARG_ARTIST to artist
            )
        }
    }
}