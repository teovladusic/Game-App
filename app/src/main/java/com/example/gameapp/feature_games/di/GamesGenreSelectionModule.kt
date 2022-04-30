package com.example.gameapp.feature_games.di

import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.data.repository.GamesRepositoryImpl
import com.example.gameapp.feature_games.data.repository.GenresRepositoryImpl
import com.example.gameapp.feature_games.domain.GamesRepository
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.mapper.GameMapper
import com.example.gameapp.feature_games.domain.model.mapper.GenreMapper
import com.example.gameapp.feature_games.domain.use_cases.GetGamesUseCase
import com.example.gameapp.feature_games.domain.use_cases.GetGenresUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GamesGenreSelectionModule {

    @Provides
    @Singleton
    fun provideGenresMapper(): EntityMapper<GenreDto, Genre> = GenreMapper()

    @Provides
    @Singleton
    fun provideGenresRepository(
        dispatcherProvider: DispatcherProvider,
        apiService: GamesApiService,
        genresMapper: EntityMapper<GenreDto, Genre>
    ): GenresRepository = GenresRepositoryImpl(dispatcherProvider, apiService, genresMapper)

    @Provides
    @Singleton
    fun provideGetGenresUseCase(
        repository: GenresRepository
    ) = GetGenresUseCase(repository)

    @Provides
    @Singleton
    fun provideGameMapper(): EntityMapper<GameDto, Game> = GameMapper()

    @Provides
    @Singleton
    fun provideGamesRepository(
        api: GamesApiService,
        mapper: EntityMapper<GameDto, Game>,
        dispatcherProvider: DispatcherProvider
    ): GamesRepository = GamesRepositoryImpl(dispatcherProvider, api, mapper)

    @Provides
    @Singleton
    fun provideGetGamesUseCase(
        repository: GamesRepository
    ) = GetGamesUseCase(repository)
}