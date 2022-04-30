package com.example.gameapp.feature_games.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.gameapp.feature_games.domain.GenreDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("genre")

class GenreDataStoreImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : GenreDataStore {

    private val dataStore = appContext.dataStore

    private val genreNameKey = stringPreferencesKey("genreName")

    override suspend fun setGenreName(name: String) {
        dataStore.edit { preferences ->
            preferences[genreNameKey] = name
        }
    }

    override suspend fun getGenreName(): Flow<String> = dataStore.data.map { preferences ->
        preferences[genreNameKey] ?: ""
    }
}