package com.example.gameapp.feature_games.domain.model.mapper

import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.domain.model.Genre

class GenreMapper : EntityMapper<GenreDto, Genre> {
    override fun mapFromDto(dto: GenreDto): Genre {
        return Genre(
            id = dto.id,
            name = dto.name,
            slug = dto.slug,
            gamesCount = dto.gamesCount,
            imageBackground = dto.imageBackground
        )
    }

    override fun mapToDto(domainModel: Genre): GenreDto {
        return GenreDto(
            id = domainModel.id,
            name = domainModel.name,
            slug = domainModel.slug,
            gamesCount = domainModel.gamesCount,
            imageBackground = domainModel.imageBackground
        )
    }
}