package da.zo.rickandmortyapi.episodes.data.di

import da.zo.rickandmortyapi.episodes.data.datasource.EpisodesDataSource
import da.zo.rickandmortyapi.episodes.data.datasource.RickAndMortyEpisodeDataSource
import da.zo.rickandmortyapi.episodes.data.repository.RickAndMortyEpisodeRepository
import da.zo.rickandmortyapi.episodes.domain.EpisodesDomainLayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Module
@InstallIn(ActivityComponent::class)
class EpisodesDataModule {

    @Provides
    fun providesEpisodeRepository(
        remoteDataSource: EpisodesDataSource.Remote,
        localDataSource: EpisodesDataSource.Local
    ): EpisodesDomainLayerContract.DataLayer.EpisodeRepository = RickAndMortyEpisodeRepository.apply {
        episodesRemoteDataSource = remoteDataSource
        episodesLocalDataSource = localDataSource
    }

    @Provides
    fun providesEpisodesRemoteDataSource(rickAndMortyEpisodeDataSource: RickAndMortyEpisodeDataSource): EpisodesDataSource.Remote = rickAndMortyEpisodeDataSource

    @Provides
    fun providesEpisodesLocalDataSource(rickAndMortyEpisodeDataSource: RickAndMortyEpisodeDataSource): EpisodesDataSource.Local = rickAndMortyEpisodeDataSource

}