package com.example.gameapp.feature_games.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gameapp.core.util.Constants
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import com.example.gameapp.feature_games.domain.model.mapper.GameMapper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

class GamesPagingSource(
    private val gamesQuery: GamesQuery,
    private val api: GamesApiService,
    private val mapper: EntityMapper<GameDto, Game>
) : PagingSource<Int, Game>() {

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val currentPage = params.key ?: 1

            val response = api.getGames(
                Constants.API_KEY,
                currentPage,
                gamesQuery.searchQuery,
                gamesQuery.genreName
            )

            if (!response.isSuccessful) {
                throw Exception(response.message())
            }

            val games = response.body()!!.results.map { mapper.mapFromDto(it) }

            val prevKey = if (response.body()!!.previous == null) null else currentPage - 1

            val nextKey = if (response.body()!!.next == null) null else currentPage + 1

            LoadResult.Page(
                data = games,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}