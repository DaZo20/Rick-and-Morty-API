package da.zo.rickandmortyapi.characters.domain.usecase

import android.widget.SearchView
import da.zo.rickandmortyapi.MainActivity
import da.zo.rickandmortyapi.R
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Character
import javax.inject.Inject

//
// Created by DaZo20 on 13/01/2023.
//
class GetCharacterByIdUc @Inject constructor(
    private val characterRepository: CharacterDomainLayerContract.DataLayer.CharacterRepository
) : CharacterDomainLayerContract.PresentationLayer.UseCase<Character> {
    override suspend fun invoke(): Result<Character> = characterRepository.getCharacterById(id = 1)
}