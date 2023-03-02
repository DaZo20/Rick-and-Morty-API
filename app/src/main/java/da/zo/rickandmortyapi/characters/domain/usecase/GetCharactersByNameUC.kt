package da.zo.rickandmortyapi.characters.domain.usecase

import androidx.fragment.app.Fragment
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.presentation.view.CharacterFragment
import da.zo.rickandmortyapi.databinding.FragmentCharacterBinding
import javax.inject.Inject

//
// Created by DaZo20 on 25/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class GetCharactersByNameUC @Inject constructor(
    private val characterRepository: CharacterDomainLayerContract.DataLayer.CharacterRepository
): CharacterDomainLayerContract.PresentationLayer.UseCase<Characters> {

    override suspend fun invoke(): Result<Characters> = characterRepository.getCharactersByName("")

}