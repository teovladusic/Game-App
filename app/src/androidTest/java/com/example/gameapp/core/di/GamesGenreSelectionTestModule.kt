package com.example.gameapp.core.di

import com.example.gameapp.core.util.AndroidTestDispatchers
import com.example.gameapp.core.util.DispatcherProvider
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
}