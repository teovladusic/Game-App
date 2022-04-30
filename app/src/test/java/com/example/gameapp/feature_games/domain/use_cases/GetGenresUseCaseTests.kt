package com.example.gameapp.feature_games.domain.use_cases

import app.cash.turbine.test
import com.example.gameapp.core.util.Resource
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.RepositoryResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetGenresUseCaseTests {

    @Test
    fun `invoke, response is error, should emit loading and error with correct data`() = runTest {
        val repositoryMock = mock(GenresRepository::class.java)

        val repositoryResult: RepositoryResult<List<Genre>> = RepositoryResult(false, null, "error")

        `when`(repositoryMock.getGenres()).thenReturn(repositoryResult)

        val useCase = GetGenresUseCase(repositoryMock)

        useCase().test {
            val firstEmission = awaitItem()
            assertThat(firstEmission).isInstanceOf(Resource.Loading::class.java)
            val secondEmission = awaitItem()
            assertThat(secondEmission).isInstanceOf(Resource.Error::class.java)
            assertThat(secondEmission.data).isNull()
            assertThat(secondEmission.message).isEqualTo("error")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke, response is success, should emit loading and success with correct data`() =
        runTest {
            val repositoryMock = mock(GenresRepository::class.java)

            val data = listOf(Genre(1, "name", "slug", 1, "imageBackground"))

            val repositoryResult: RepositoryResult<List<Genre>> = RepositoryResult(true, data, null)

            `when`(repositoryMock.getGenres()).thenReturn(repositoryResult)

            val useCase = GetGenresUseCase(repositoryMock)

            useCase().test {
                val firstEmission = awaitItem()
                assertThat(firstEmission).isInstanceOf(Resource.Loading::class.java)
                val secondEmission = awaitItem()
                assertThat(secondEmission).isInstanceOf(Resource.Success::class.java)
                assertThat(secondEmission.message).isNull()
                assertThat(secondEmission.data).isEqualTo(data)

                cancelAndIgnoreRemainingEvents()
            }
        }
}