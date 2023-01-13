package da.zo.rickandmortyapi.characters.domain

import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.domain.model.Character

//
// Created by DaZo20 on 11/01/2023.
//
interface CharacterDomainLayerContract {
    interface PresentationLayer {
        interface UseCase<T> {
            suspend operator fun invoke(): Result<T>
        }
    }

    interface DataLayer {
        interface CharacterRepository {
            suspend fun getAllCharactersList(): Result<Characters>

            suspend fun getCharactersNextPage(): Result<Characters>

            suspend fun getCharacterById(id: Int): Result<Character>

            suspend fun getMultipleCharactersById(ids: List<Int>): Result<Characters>

            suspend fun getCharactersByStatus(status: String): Result<Characters>
        }
    }
}