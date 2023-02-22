package da.zo.rickandmortyapi.episodes.domain.di


import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.episodes.domain.EpisodesDomainLayerContract
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import da.zo.rickandmortyapi.episodes.domain.usecase.GetAllEpisodesUC
import da.zo.rickandmortyapi.episodes.domain.usecase.GetEpisodesNextPageUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Module
@InstallIn(ActivityComponent::class)
class EpisodesDomainModule {

    @Provides
    @Named("get_all_episodes")
    fun providesGetAllEpisodesUC(usecase: GetAllEpisodesUC): @JvmSuppressWildcards EpisodesDomainLayerContract.PresentationLayer.UseCase<Episodes> = usecase

    @Provides
    @Named("get_episodes_next_page")
    fun providesGetEpisodesNextPageUc(getNexPageUc: GetEpisodesNextPageUC) : @JvmSuppressWildcards EpisodesDomainLayerContract.PresentationLayer.UseCase<Episodes> = getNexPageUc

}
