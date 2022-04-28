package com.example.gameapp.feature_games.di

import com.example.gameapp.core.util.DefaultDispatchers
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.data.repository.GenresRepositoryImpl
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.mapper.GenreMapper
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
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatchers()

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
}