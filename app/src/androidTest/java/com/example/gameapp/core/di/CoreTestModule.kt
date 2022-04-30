package com.example.gameapp.core.di

import android.content.Context
import com.example.gameapp.core.util.AndroidTestDispatchers
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.feature_games.data.local.GenreDataStoreImpl
import com.example.gameapp.feature_games.data.local.GenresDataStoreFakeImpl
import com.example.gameapp.feature_games.domain.GenreDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreModule::class]
)
object CoreTestModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = AndroidTestDispatchers()

    @Provides
    @Singleton
    fun provideGenreDataStore(): GenreDataStore = GenresDataStoreFakeImpl()
}