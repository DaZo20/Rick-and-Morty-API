package da.zo.rickandmortyapi.characters.domain.usecase

import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import javax.inject.Inject

//
// Created by DaZo20 on 12/06/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class GetCharactersByStatusUc @Inject constructor(
    private val characterRepository: CharacterDomainLayerContract.DataLayer.CharacterRepository
) {
    suspend operator fun invoke(status: String): Result<Characters> =
        characterRepository.getCharactersByStatus(status)
}