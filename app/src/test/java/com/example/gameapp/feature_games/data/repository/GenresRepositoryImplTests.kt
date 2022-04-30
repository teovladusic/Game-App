package com.example.gameapp.feature_games.data.repository

import com.example.gameapp.core.util.TestDispatchers
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.dto.GenreResponseDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.RepositoryResult
import com.example.gameapp.feature_games.domain.model.mapper.GenreMapper
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class GenresRepositoryImplTests {

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

    private lateinit var repository: GenresRepository

    private val mapper = GenreMapper()

    @Before
    fun setUp() {
        repository = GenresRepositoryImpl(testDispatchers, api, mapper)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private val gson = Gson()


    @Test
    fun `getGenres, response is null, should return correct RepositoryResult`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
        )

        val response = repository.getGenres()

        val expectedResponse = RepositoryResult(false, null, "An error occurred.")

        assertThat(response).isEqualTo(expectedResponse)
    }

    @Test
    fun `getGenres, response is not null, should return correct RepositoryResult`() = runTest {
        val genresDtoList = listOf(GenreDto(1, "name", "slug", 1, "imageBackground"))
        val genreResponseDto = GenreResponseDto(1, genresDtoList)

        val genresDomainModelList = listOf(Genre(1, "name", "slug", 1, "imageBackground"))

        val jsonBody = gson.toJson(genreResponseDto)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonBody)
        )

        val response = repository.getGenres()

        val expectedResponse = RepositoryResult(true, genresDomainModelList, null)

        assertThat(response).isEqualTo(expectedResponse)
    }
}