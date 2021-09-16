package dev.vladimirj.tidal.search.data.repo

import dev.vladimirj.tidal.search.data.SearchService
import dev.vladimirj.tidal.search.data.entity.SearchResponse
import dev.vladimirj.tidal.search.domain.entity.Artist
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import dev.vladimirj.tidal.search.domain.SearchArtistsResult

class ArtistRepositoryImpl(
    private val searchService: SearchService
): ArtistRepository {
    override suspend fun searchForArtists(searchTerm: String): SearchArtistsResult {
        return getSearchResultsAndMapThem { searchService.search(searchTerm) }
    }

    override suspend fun getMoreSearchResults(url: String): SearchArtistsResult {
        return getSearchResultsAndMapThem { searchService.getMoreSearchResults(url) }
    }

    private suspend fun getSearchResultsAndMapThem(provider: (suspend () -> SearchResponse)): SearchArtistsResult {
        return try {
            val result = provider()
            val listOfArtists = result.data.map {
                Artist(
                    id = it.id,
                    name = it.name,
                    picture = it.picture
                )
            }
            SearchArtistsResult.Success(listOfArtists, result.total, result.next)
        } catch (throwable: Throwable) {
            SearchArtistsResult.Error(throwable.localizedMessage)
        }
    }
}