package com.example.gameapp.feature_games.di

import com.example.gameapp.core.util.AndroidTestDispatchers
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.repository.GenresRepositoryFakeImpl
import com.example.gameapp.feature_games.di.GamesGenreSelectionModule
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.mapper.GenreMapper
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
    fun provideDispatcherProvider(): DispatcherProvider = AndroidTestDispatchers()

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
}