package com.example.gameapp.feature_games.data.remote

import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.dto.GenreResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("genres")
    suspend fun getGenres(@Query("key") key: String): Response<GenreResponseDto>
}