package da.zo.rickandmortyapi.episodes.domain.usecase

import da.zo.rickandmortyapi.episodes.domain.EpisodesDomainLayerContract
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import javax.inject.Inject

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class GetEpisodesNextPageUC @Inject constructor(
    private val episodeRepository: EpisodesDomainLayerContract.DataLayer.EpisodeRepository
): EpisodesDomainLayerContract.PresentationLayer.UseCase<Episodes> {
    override suspend fun invoke(): Result<Episodes> = episodeRepository.getEpisodesNextPage()

}