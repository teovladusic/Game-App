package com.example.gameapp.feature_games.domain.model.mapper

import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.domain.model.Genre
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class GenreMapperTests {

    lateinit var mapper: EntityMapper<GenreDto, Genre>

    @Before
    fun setUp() {
        mapper = GenreMapper()
    }

    @Test
    fun mapFromDto_shouldCorrectlyMap() {

        val dtoToMapFrom = GenreDto(1, "name", "slug", 1, "imageBackground")

        val expectedResult = Genre(1, "name", "slug", 1, "imageBackground")

        val result = mapper.mapFromDto(dtoToMapFrom)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun mapToDto_shouldCorrectlyMap() {

        val domainModelToMapFrom = Genre(1, "name", "slug", 1, "imageBackground")

        val expectedResult = GenreDto(1, "name", "slug", 1, "imageBackground")

        val result = mapper.mapToDto(domainModelToMapFrom)

        assertThat(result).isEqualTo(expectedResult)
    }
}