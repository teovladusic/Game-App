package com.example.gameapp.feature_games.domain.use_cases

import com.example.gameapp.core.util.Resource
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenresRepository
) {

    suspend operator fun invoke() = flow<Resource<List<Genre>>> {
        emit(Resource.Loading())
        val genresRepositoryResponse = repository.getGenres()

        if (genresRepositoryResponse.isSuccess) {
            emit(Resource.Success(data = genresRepositoryResponse.data ?: emptyList()))
        } else {
            emit(
                Resource.Error(
                    data = null,
                    message = genresRepositoryResponse.errorMessage ?: "Unknown error appeared."
                )
            )
        }
    }
}