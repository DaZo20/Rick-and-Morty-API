package da.zo.rickandmortyapi.episodes.domain

import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.episodes.domain.model.Episodes

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
interface EpisodesDomainLayerContract {
    interface PresentationLayer {

        interface UseCase<T> {
            suspend operator fun invoke(): Result<T>
        }

    }

    interface DataLayer {

        interface EpisodeRepository {
            suspend fun getAllEpisodesList() : Result<Episodes>
            suspend fun getEpisodesNextPage(): Result<Episodes>
        }

    }
}