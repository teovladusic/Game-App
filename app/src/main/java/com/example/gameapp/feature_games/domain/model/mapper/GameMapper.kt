package com.example.gameapp.feature_games.domain.model.mapper

import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.domain.model.Game

class GameMapper : EntityMapper<GameDto, Game> {

    override fun mapFromDto(dto: GameDto): Game {
        return Game(
            id = dto.id,
            name = dto.name,
            backgroundImage = dto.backgroundImage ?: "",
            rating = dto.rating,
            ratingsCount = dto.ratingsCount,
            genres = dto.genres.map { it.name },
            shortScreenshots = dto.shortScreenshots.map { it.image },
            suggestionsCount = dto.suggestionsCount
        )
    }

    override fun mapToDto(domainModel: Game): GameDto {
        throw Exception("mapping to dto is not needed.")
    }
}