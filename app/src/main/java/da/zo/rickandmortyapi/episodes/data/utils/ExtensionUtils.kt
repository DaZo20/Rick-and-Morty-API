package da.zo.rickandmortyapi.episodes.data.utils

import da.zo.rickandmortyapi.episodes.data.db.EpisodeEntity
import da.zo.rickandmortyapi.episodes.data.model.EpisodeDto
import da.zo.rickandmortyapi.episodes.data.model.EpisodesDto
import da.zo.rickandmortyapi.episodes.domain.model.Episode

import da.zo.rickandmortyapi.episodes.domain.model.Episodes

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

fun EpisodesDto?.toEpisodes() : Episodes =
    Episodes(results = this?.results?.toEpisodeList() ?: emptyList())

private fun List<EpisodeDto>.toEpisodeList() : List<Episode> =
    map { dto->
        with(dto){
            Episode(
                id = id,
                name = name,
                airDate = airDate,
                episode = episode,
                characters = characters,
                url = url,
                created = created
            )
        }
    }

fun EpisodesDto.toEpisodesEntity(page: Int): List<EpisodeEntity> = results.map { dto ->
    with(dto){
        EpisodeEntity(
            id = id,
            page = page,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = characters,
            url = url,
            created = created
        )
    }
}

fun List<EpisodeEntity>.toEpisodes(): Episodes =
    Episodes(
        results = map { entity ->
            with(entity) {
                Episode(
                    id= id,
                    name= name,
                    airDate = airDate,
                    episode = episode,
                    characters = characters,
                    url = url,
                    created = created
                )
            }
        }
    )
