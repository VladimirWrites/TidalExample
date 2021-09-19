package dev.vladimirj.tidal.search.ui.artists

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.base.ui.Event
import dev.vladimirj.tidal.base.ui.addOnPropertyChanged
import dev.vladimirj.tidal.base.ui.debounce
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.usecase.LoadMoreArtists
import dev.vladimirj.tidal.search.domain.usecase.SearchArtists
import dev.vladimirj.tidal.search.domain.entity.Artist
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val QUERY_DEBOUNCE_INTERVAL_MS = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtists: SearchArtists,
    private val loadMoreArtists: LoadMoreArtists,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private var nextResults: String?  = null

    private val mutableUiEvents = MutableLiveData<Event<UiEvent>>()
    val uiEvents: LiveData<Event<UiEvent>> = mutableUiEvents

    private val mutableSearchResults = MutableLiveData<List<SearchResultsUiModel>>()
    val searchResults: LiveData<List<SearchResultsUiModel>> = mutableSearchResults

    val isProgressVisible: ObservableBoolean = ObservableBoolean(false)

    val isNoResultsVisible: ObservableBoolean = ObservableBoolean(false)

    private val debounceTextInput: (String) -> Unit =
        viewModelScope.debounce(QUERY_DEBOUNCE_INTERVAL_MS, coroutineDispatcherProvider.io) { searchTerm ->
            if (searchTerm.isBlank()) {
                mutableSearchResults.postValue(emptyList())
                return@debounce
            }
            nextResults = null
            searchForArtists(searchTerm)
        }

    val searchQuery = ObservableField<String>().apply {
        addOnPropertyChanged {
            debounceTextInput(it.get().orEmpty())
        }
    }

    fun cancelSearch() {
        searchQuery.set("")
    }

    fun loadMore() {
        if(!nextResults.isNullOrBlank()) {
            loadMoreSearchResults(nextResults!!)
        }
    }

    private fun searchForArtists(searchTerm: String) {

        if (searchResults.value.isNullOrEmpty()) {
            isProgressVisible.set(true)
        }
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            val result = searchArtists(searchTerm)
            withContext(coroutineDispatcherProvider.main) {
                isProgressVisible.set(false)
                when (result) {
                    is DomainResult.Success<*> -> {
                        nextResults = result.next
                        val aggregatedSearchResults = mutableListOf<SearchResultsUiModel>()

                        if(result.data.isNotEmpty()) {
                            aggregatedSearchResults.add(SearchResultsUiModel.HeaderUiModel)
                            isNoResultsVisible.set(false)
                        } else {
                            isNoResultsVisible.set(true)
                        }

                        result.data.forEach {
                            val artist = it as Artist
                            aggregatedSearchResults.add(artist.toArtistUiModel {
                                mutableUiEvents.postValue(Event(UiEvent.GoToAlbums(artist)))
                            })
                        }
                        mutableSearchResults.postValue(aggregatedSearchResults)
                        mutableUiEvents.postValue(Event(UiEvent.ScrollToTop))
                    }
                    is DomainResult.Error -> {
                        mutableUiEvents.postValue(Event(UiEvent.ShowError(result.message)))
                    }
                }
            }
        }
    }

    private fun loadMoreSearchResults(url: String) {

        if (searchResults.value.isNullOrEmpty()) {
            isProgressVisible.set(true)
        }
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            val result = loadMoreArtists(url)
            withContext(coroutineDispatcherProvider.main) {
                isProgressVisible.set(false)
                when (result) {
                    is DomainResult.Success<*> -> {
                        val aggregatedSearchResults = if(searchResults.value.isNullOrEmpty())
                            mutableListOf<SearchResultsUiModel>(SearchResultsUiModel.HeaderUiModel)
                        else searchResults.value!!.toMutableList()
                        nextResults = result.next

                        result.data.forEach {
                            val artist = it as Artist
                            aggregatedSearchResults.add(artist.toArtistUiModel {
                                mutableUiEvents.postValue(Event(UiEvent.GoToAlbums(artist)))
                            })
                        }
                        mutableSearchResults.postValue(aggregatedSearchResults)
                    }
                    is DomainResult.Error -> {
                        mutableUiEvents.postValue(Event(UiEvent.ShowError(result.message)))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowError(val message: String) : UiEvent()
        data class GoToAlbums(val artist: Artist) : UiEvent()
        object ScrollToTop : UiEvent()
    }
}