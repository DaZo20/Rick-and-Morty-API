package da.zo.rickandmortyapi.episodes.data.repository

import da.zo.rickandmortyapi.episodes.data.datasource.EpisodesDataSource
import da.zo.rickandmortyapi.episodes.data.utils.toEpisodes
import da.zo.rickandmortyapi.episodes.data.utils.toEpisodesEntity
import da.zo.rickandmortyapi.episodes.domain.EpisodesDomainLayerContract
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

object RickAndMortyEpisodeRepository : EpisodesDomainLayerContract.DataLayer.EpisodeRepository {

    private var nextPage: Int = 1
    lateinit var episodesRemoteDataSource: EpisodesDataSource.Remote
    lateinit var episodesLocalDataSource: EpisodesDataSource.Local

    override suspend fun getAllEpisodesList(): Result<Episodes> =
        try {
            episodesRemoteDataSource.getAllEpisodesListResponse().map { dto ->
                dto?.toEpisodes()?.also {
                    withContext(Dispatchers.IO) {
                        episodesLocalDataSource.saveEpisodesList(list = dto.toEpisodesEntity(page = nextPage))
                        nextPage++
                    }
                } ?: episodesLocalDataSource.fetchEpisodesList().toEpisodes()
            }
        } catch (e: Exception) {
            Result.success(episodesLocalDataSource.fetchEpisodesList().toEpisodes())
        }

    override suspend fun getEpisodesNextPage(): Result<Episodes> =
        try {
            episodesRemoteDataSource.getEpisodesNextPage(page = nextPage).map { dto ->
                dto?.toEpisodes()?.also {
                    withContext(Dispatchers.IO) {
                        episodesLocalDataSource.saveEpisodesList(
                            list = dto.toEpisodesEntity(page = nextPage)
                        )
                        nextPage++
                    }
                } ?: run {
                    withContext(Dispatchers.IO) {
                        episodesLocalDataSource.fetchEpisodesNextPage(page = nextPage)
                            .toEpisodes()
                            .also {
                                if (it.results.isNotEmpty()) {
                                    nextPage++
                                }
                            }
                    }
                }
            }

        } catch (e: Exception) {
            Result.success(
                withContext(Dispatchers.IO) {
                    episodesLocalDataSource.fetchEpisodesNextPage(page = nextPage).toEpisodes()
                        .also {
                            if (it.results.isNotEmpty()) {
                                nextPage++
                            }
                        }
                }
            )
        }

}