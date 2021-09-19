package dev.vladimirj.tidal.search.ui.artists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.vladimirj.tidal.base.ui.addOnScrolledToBottom
import dev.vladimirj.tidal.base.ui.observe
import dev.vladimirj.tidal.base.ui.observeEvent
import dev.vladimirj.tidal.search.ui.R
import dev.vladimirj.tidal.search.ui.SearchNavigator
import dev.vladimirj.tidal.search.ui.artists.SearchViewModel.UiEvent.*
import dev.vladimirj.tidal.search.ui.databinding.FragmentSearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: FragmentSearchBinding

    private val adapter = SearchResultsAdapter()

    @Inject
    lateinit var navigator: SearchNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        observeEvent(viewModel.uiEvents) {
            when (it) {
                is ShowError -> {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is ScrollToTop -> binding.recyclerArtists.scrollToPosition(0)
                is GoToAlbums -> navigator.goToAlbums(requireActivity(), it.artist.toParcelableArtist())
            }
        }
        observe(viewModel.searchResults) {
            adapter.submitList(it)
        }

        binding.recyclerArtists.adapter = adapter
        binding.recyclerArtists.itemAnimator = null

        binding.recyclerArtists.addOnScrolledToBottom {
            viewModel.loadMore()
        }
    }

    companion object {
        const val TAG = "SearchFragment"

        fun newInstance() = SearchFragment()
    }

}