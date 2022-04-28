package com.example.gameapp.core.di

import android.content.Context
import com.example.gameapp.core.util.Constants
import com.example.gameapp.feature_games.data.local.GenreDataStoreImpl
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.domain.GenreDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.API_BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideGamesApiService(retrofit: Retrofit): GamesApiService =
        retrofit.create(GamesApiService::class.java)

    @Provides
    @Singleton
    fun provideGenreDataStore(
        @ApplicationContext appContext: Context
    ): GenreDataStore = GenreDataStoreImpl(appContext)

}