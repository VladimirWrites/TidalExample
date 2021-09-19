package dev.vladimirj.tidal.search.ui.album

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
import dev.vladimirj.tidal.search.domain.entity.Track
import dev.vladimirj.tidal.search.domain.usecase.GetTracks
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val getTracks: GetTracks,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private var nextResults: String? = null

    private val mutableUiEvents = MutableLiveData<Event<UiEvent>>()
    val uiEvents: LiveData<Event<UiEvent>> = mutableUiEvents

    private val mutableAlbumResults = MutableLiveData<List<AlbumDetailsUiModel>>()
    val albumResults: LiveData<List<AlbumDetailsUiModel>> = mutableAlbumResults

    val isProgressVisible: ObservableBoolean = ObservableBoolean(false)

    private lateinit var currentAlbum: Album

    fun loadAlbum(album: Album, artist: Artist) {
        currentAlbum = album
        if (albumResults.value.isNullOrEmpty()) {
            isProgressVisible.set(true)
        }
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            val result = getTracks(currentAlbum.id)
            withContext(coroutineDispatcherProvider.main) {
                isProgressVisible.set(false)
                when (result) {
                    is DomainResult.Success<*> -> {
                        nextResults = result.next

                        val tracks = result.data.map { it as Track }
                        val albumDetailsUiModels =  tracks.toAlbumDetailsModels(album, artist)
                        mutableAlbumResults.postValue(albumDetailsUiModels)
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
    }
}