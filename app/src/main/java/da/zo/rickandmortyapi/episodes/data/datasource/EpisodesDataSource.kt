package da.zo.rickandmortyapi.episodes.data.datasource

import da.zo.rickandmortyapi.common.db.ApplicationDatabase
import da.zo.rickandmortyapi.episodes.data.api.EpisodesService
import da.zo.rickandmortyapi.episodes.data.db.EpisodeEntity
import da.zo.rickandmortyapi.episodes.data.model.EpisodesDto
import da.zo.rickandmortyapi.episodes.domain.model.Episode
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
interface EpisodesDataSource {

    interface Remote {
        suspend fun getAllEpisodesListResponse(): Result<EpisodesDto?>
        suspend fun getEpisodesNextPage(page: Int): Result<EpisodesDto?>
    }

    interface Local {
        suspend fun saveEpisodesList(list: List<EpisodeEntity>)
        suspend fun fetchEpisodesList(): List<EpisodeEntity>
        suspend fun fetchEpisodesNextPage(page: Int): List<EpisodeEntity>
    }

}

class RickAndMortyEpisodeDataSource @Inject constructor(
    private val retrofitInstance: Retrofit,
    private val roomDataBaseInstance: ApplicationDatabase
        ) : EpisodesDataSource.Remote, EpisodesDataSource.Local {

    override suspend fun getAllEpisodesListResponse(): Result<EpisodesDto?> =
        retrofitInstance.create(EpisodesService::class.java).getAllEpisodesList().runCatching { body() }

    override suspend fun getEpisodesNextPage(page: Int): Result<EpisodesDto?> =
        retrofitInstance.create(EpisodesService::class.java).getAllEpisodesList(page = page)
            .runCatching { body() }

    override suspend fun saveEpisodesList(list: List<EpisodeEntity>) =
        roomDataBaseInstance.episodeDao().insertAll(*list.toTypedArray())


    override suspend fun fetchEpisodesList(): List<EpisodeEntity> =
        roomDataBaseInstance.episodeDao().getAllEpisodes()

    override suspend fun fetchEpisodesNextPage(page: Int): List<EpisodeEntity> =
        roomDataBaseInstance.episodeDao().getEpisodesByPage(page = page)


}