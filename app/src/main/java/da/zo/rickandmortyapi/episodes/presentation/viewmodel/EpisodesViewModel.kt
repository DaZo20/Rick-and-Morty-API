package da.zo.rickandmortyapi.episodes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.episodes.domain.EpisodesDomainLayerContract
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import da.zo.rickandmortyapi.episodes.domain.usecase.GetAllEpisodesUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class EpisodesViewModel @Inject constructor(
    @Named("get_all_episodes") val getAllEpisodesUC: EpisodesDomainLayerContract.PresentationLayer.UseCase<Episodes>,
    @Named("get_episodes_next_page") val getEpisodesNextPageUC: EpisodesDomainLayerContract.PresentationLayer.UseCase<Episodes>
) : ViewModel() {

    val episodes: StateFlow<Episodes?>
        get() = _episodes.asStateFlow()

    private var _episodes: MutableStateFlow<Episodes?> = MutableStateFlow(null)

    init {
        fetchEpisodesData()
    }

    private fun fetchEpisodesData() {
        viewModelScope.launch {
            getAllEpisodesUC().onSuccess { ep ->
                _episodes.update { ep }
            }.onFailure { th ->
                th.printStackTrace()
            }
        }
    }

    fun onEndOfScrollReached() {
        viewModelScope.launch {
            getEpisodesNextPageUC().onSuccess { ep ->
                _episodes.value = ep
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

}