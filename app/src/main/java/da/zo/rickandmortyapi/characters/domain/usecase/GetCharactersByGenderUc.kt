package da.zo.rickandmortyapi.characters.domain.usecase

import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import javax.inject.Inject

//
// Created by DaZo20 on 03/06/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class GetCharactersByGenderUc @Inject constructor(
    private val characterRepository: CharacterDomainLayerContract.DataLayer.CharacterRepository
) {
    suspend operator fun invoke(gender: String): Result<Characters> =
        characterRepository.getCharactersByGender(gender)
}