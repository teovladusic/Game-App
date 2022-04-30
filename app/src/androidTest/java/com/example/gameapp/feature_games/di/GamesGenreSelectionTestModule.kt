package com.example.gameapp.feature_games.di

import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.repository.GamesRepositoryFakeImpl
import com.example.gameapp.feature_games.data.repository.GenresRepositoryFakeImpl
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
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GamesGenreSelectionModule::class]
)
object GamesGenreSelectionTestModule {

    @Provides
    @Singleton
    fun provideGenresMapper(): EntityMapper<GenreDto, Genre> = GenreMapper()

    @Provides
    @Singleton
    fun provideGenresRepository(): GenresRepository = GenresRepositoryFakeImpl()

    @Provides
    @Singleton
    fun provideGetGenresUseCase(
        repository: GenresRepository
    ) = GetGenresUseCase(repository)

    @Provides
    @Singleton
    fun provideGamesRepository(): GamesRepository = GamesRepositoryFakeImpl()

    @Provides
    @Singleton
    fun provideGetGamesUseCase(
        repository: GamesRepository
    ) = GetGamesUseCase(repository)

    @Provides
    @Singleton
    fun provideGamesMapper(): EntityMapper<GameDto, Game> = GameMapper()

}