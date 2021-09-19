package dev.vladimirj.tidal.search.data

import dev.vladimirj.tidal.search.data.entity.RemoteRecording
import dev.vladimirj.tidal.search.data.entity.RemoteArtist
import dev.vladimirj.tidal.search.data.entity.RemoteResponse
import dev.vladimirj.tidal.search.data.entity.RemoteTrack
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
    suspend fun getRecordings(
        @Path("artist_id") artistId: Long
    ): RemoteResponse<RemoteRecording>

    @GET
    suspend fun getMoreRecordings(
        @Url url: String
    ): RemoteResponse<RemoteRecording>

    @GET("album/{album_id}/tracks")
    suspend fun getTracks(
        @Path("album_id") albumId: Long,
        @Query("limit") limit: Int = 300 // Record for most tracks on one album is 300 and is held by album 'Modern Day Masterpiece' by Orlando “Jahlil Beats” Tucker. TODO: Add pagination and remove limit
    ): RemoteResponse<RemoteTrack>
}