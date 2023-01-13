package da.zo.rickandmortyapi.characters.domain.usecase

import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import javax.inject.Inject

//
// Created by DaZo20 on 13/01/2023.
//
class GetCharacterNextPageUc @Inject constructor(
    private val characterRepository: CharacterDomainLayerContract.DataLayer.CharacterRepository
): CharacterDomainLayerContract.PresentationLayer.UseCase<Characters> {
    override suspend fun invoke(): Result<Characters> = characterRepository.getCharactersNextPage()
}