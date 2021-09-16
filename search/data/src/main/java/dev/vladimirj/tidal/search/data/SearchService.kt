package dev.vladimirj.tidal.search.data

import dev.vladimirj.tidal.search.data.entity.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SearchService {
    @GET("search/artist")
    suspend fun search(
        @Query("q") searchQuery: String
    ): SearchResponse

    @GET
    suspend fun getMoreSearchResults(@Url url: String): SearchResponse
}