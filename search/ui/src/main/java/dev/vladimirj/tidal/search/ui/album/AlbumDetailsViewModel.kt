package dev.vladimirj.tidal.search.ui.album

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vladimirj.tidal.base.ui.CoroutineDispatcherProvider
import dev.vladimirj.tidal.base.ui.Event
import dev.vladimirj.tidal.search.domain.DomainResult
import dev.vladimirj.tidal.search.domain.entity.Album
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.entity.Track
import dev.vladimirj.tidal.search.domain.usecase.GetAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetMoreAlbums
import dev.vladimirj.tidal.search.domain.usecase.GetTracks
import dev.vladimirj.tidal.search.ui.artists.SearchResultsUiModel
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
                        val albumDetailsUiModels = mutableListOf<AlbumDetailsUiModel>()
                        albumDetailsUiModels.add(0, AlbumDetailsUiModel.HeaderUiModel(
                            album, artist
                        ))

                        val tracks = result.data.map { it as Track }
                        val groupedByDiskNumber = tracks.groupBy { it.diskNumber }

                        if(groupedByDiskNumber.keys.size == 1) {
                            tracks.sortedBy { it.trackPosition }.forEach {
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