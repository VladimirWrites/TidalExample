package dev.vladimirj.tidal.search.data

import dev.vladimirj.tidal.search.data.entity.RemoteAlbum
import dev.vladimirj.tidal.search.data.entity.RemoteArtist
import dev.vladimirj.tidal.search.data.entity.RemoteResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface SearchService {
    @GET("search/artist")
    suspend fun search(
        @Query("q") searchQuery: String
    ): RemoteResponse<RemoteArtist>

    @GET
    suspend fun getMoreSearchResults(
        @Url url: String
    ): RemoteResponse<RemoteArtist>

    @GET("artist/{artist_id}/albums")
    suspend fun getAlbums(
        @Path("artist_id") artistId: Long
    ): RemoteResponse<RemoteAlbum>

    @GET
    suspend fun getMoreAlbums(
        @Url url: String
    ): RemoteResponse<RemoteAlbum>
}