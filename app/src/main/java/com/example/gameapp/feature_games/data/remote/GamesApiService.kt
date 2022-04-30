package com.example.gameapp.feature_games.data.remote

import com.example.gameapp.feature_games.data.dto.GameResponseDto
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.dto.GenreResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("genres")
    suspend fun getGenres(@Query("key") key: String): Response<GenreResponseDto>

    @GET("games")
    suspend fun getGames(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("search") searchQuery: String?,
        @Query("genres") genreName: String
    ) : Response<GameResponseDto>
}