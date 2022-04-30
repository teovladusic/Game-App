package com.example.gameapp.feature_games.data.repository

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.gameapp.core.util.TestDispatchers
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.dto.GameResponseDto
import com.example.gameapp.feature_games.data.dto.GenreNamesDto
import com.example.gameapp.feature_games.data.dto.ScreenshotDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.domain.GamesRepository
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import com.example.gameapp.feature_games.domain.model.mapper.GameMapper
import com.example.gameapp.feature_games.domain.model.mapper.GenreMapper
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class GamesRepositoryImplTests {

    private val testDispatchers = TestDispatchers()

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(GamesApiService::class.java)

    private val mapper = GameMapper()

    private val gson = Gson()

    @Test
    fun `getGames, success, should return pagingData flow`() = runTest {
        val repository = GamesRepositoryImpl(testDispatchers, api, mapper)

        val query = GamesQuery("indie", "")

        val gamesDto = listOf(
            GameDto(
                1,
                "game1",
                "backgroundImage",
                5.0f,
                20,
                listOf(GenreNamesDto(1, "indie")),
                listOf(ScreenshotDto(1, "image")),
                20
            ),
            GameDto(
                2,
                "game2",
                "backgroundImage",
                5.0f,
                20,
                listOf(GenreNamesDto(1, "indie")),
                listOf(ScreenshotDto(1, "image")),
                20
            )
        )

        val data = GameResponseDto(null, null, gamesDto)

        val jsonBody = gson.toJson(data)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonBody)
        )

        repository.getGames(query).test {
            val flow = awaitItem()
            assertThat(flow).isInstanceOf(PagingData::class.java)
        }
    }
}