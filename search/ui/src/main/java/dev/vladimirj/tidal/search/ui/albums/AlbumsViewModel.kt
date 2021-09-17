package dev.vladimirj.tidal.search.ui.albums

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.base.ui.Event
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.usecase.GetAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetMoreAlbums
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val getAlbums: GetAlbums,
    private val getMoreAlbums: GetMoreAlbums,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private var nextResults: String? = null

    private val mutableUiEvents = MutableLiveData<Event<UiEvent>>()
    val uiEvents: LiveData<Event<UiEvent>> = mutableUiEvents

    private val mutableAlbumResults = MutableLiveData<List<AlbumUiModel>>()
    val albumResults: LiveData<List<AlbumUiModel>> = mutableAlbumResults

    val isProgressVisible: ObservableBoolean = ObservableBoolean(false)

    fun loadMore() {
        if (!nextResults.isNullOrBlank()) {
            loadMoreAlbums(nextResults!!)
        }
    }

    private lateinit var currentArtist: Artist

    fun loadAlbums(artist: Artist) {
        currentArtist = artist
        if (albumResults.value.isNullOrEmpty()) {
            isProgressVisible.set(true)
        }
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            val result = getAlbums(artist.id)
            withContext(coroutineDispatcherProvider.main) {
                isProgressVisible.set(false)
                when (result) {
                    is DomainResult.Success<*> -> {
                        nextResults = result.next
                        val aggregatedSearchResults = mutableListOf<AlbumUiModel>()

                        result.data.forEach {
                            val album = it as Album
                            aggregatedSearchResults.add(album.toAlbumUiModel(currentArtist) {
                                mutableUiEvents.postValue(Event(UiEvent.GoToTracks(currentArtist, album)))
                            })
                        }
                        mutableAlbumResults.postValue(aggregatedSearchResults)
                    }
                    is DomainResult.Error -> {
                        mutableUiEvents.postValue(Event(UiEvent.ShowError(result.message)))
                    }
                }
            }
        }
    }

    private fun loadMoreAlbums(url: String) {

        if (albumResults.value.isNullOrEmpty()) {
            isProgressVisible.set(true)
        }
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            val result = getMoreAlbums(url)
            withContext(coroutineDispatcherProvider.main) {
                isProgressVisible.set(false)
                when (result) {
                    is DomainResult.Success<*> -> {
                        val aggregatedSearchResults = if (albumResults.value.isNullOrEmpty())
                            mutableListOf()
                        else albumResults.value!!.toMutableList()

                        nextResults = result.next

                        result.data.forEach {
                            val album = it as Album
                            aggregatedSearchResults.add(album.toAlbumUiModel(currentArtist) {
                                mutableUiEvents.postValue(Event(UiEvent.GoToTracks(currentArtist, album)))
                            })
                        }
                        mutableAlbumResults.postValue(aggregatedSearchResults)
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
        data class GoToTracks(val artist: Artist, val album: Album) : UiEvent()
    }
}