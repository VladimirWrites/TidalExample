package dev.vladimirj.tidal.search.data.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vladimirj.tidal.search.data.SearchService
import dev.vladimirj.tidal.search.data.repo.ArtistRepositoryImpl
import dev.vladimirj.tidal.search.domain.repo.ArtistRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchDataModule {

    @Singleton
    @Provides
    fun provideSearchService(): SearchService {
        val okHttpClient = OkHttpClient.Builder().build()
        val moshiFactory = MoshiConverterFactory.create(Moshi.Builder().build())

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(moshiFactory)
            .baseUrl("https://api.deezer.com")
            .build()
        return retrofit.create(SearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideArtistRepository(searchService: SearchService): ArtistRepository {
        return ArtistRepositoryImpl(searchService)
    }
}