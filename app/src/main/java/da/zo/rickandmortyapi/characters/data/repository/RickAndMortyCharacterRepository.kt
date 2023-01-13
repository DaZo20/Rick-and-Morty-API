package da.zo.rickandmortyapi.characters.data.repository

import da.zo.rickandmortyapi.characters.data.datasource.CharactersDataSource
import da.zo.rickandmortyapi.characters.data.utils.toCharacters
import da.zo.rickandmortyapi.characters.data.utils.toCharactersEntity
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

//
// Created by DaZo20 on 11/01/2023.
//

@Singleton
object RickAndMortyCharacterRepository : CharacterDomainLayerContract.DataLayer.CharacterRepository {

    private var nextPage: Int = 1
    lateinit var charactersRemoteDataSource: CharactersDataSource.Remote
    lateinit var charactersLocalDataSource: CharactersDataSource.Local


    override suspend fun getAllCharactersList(): Result<Characters> =
        try {
            charactersRemoteDataSource.getAllCharactersListResponse().map { dto ->
                dto?.toCharacters()?.also {
                    withContext(Dispatchers.IO) {
                        charactersLocalDataSource.saveCharacterList(list = dto.toCharactersEntity(page = nextPage))
                        nextPage++
                    }
                } ?: charactersLocalDataSource.fetchCharacterList().toCharacters()
            }
        } catch (e: Exception) {
            Result.success(charactersLocalDataSource.fetchCharacterList().toCharacters())
        }


    override suspend fun getCharactersNextPage(): Result<Characters> =
        try {
            charactersRemoteDataSource.getCharactersNextPage(page = nextPage).map { dto ->
                dto?.toCharacters()?.also {
                    withContext(Dispatchers.IO) {
                        charactersLocalDataSource.saveCharacterList(list = dto.toCharactersEntity(page = nextPage))
                        nextPage++
                    }
                } ?:run {
                    withContext(Dispatchers.IO) {
                        charactersLocalDataSource.fetchCharacterNextPage(page = nextPage).toCharacters()
                            .also { if (it.results.isNotEmpty()) { nextPage++}
                            }
                    }
                }
            }
        } catch (e: Exception) {
            Result.success(
                withContext(Dispatchers.IO){
                    charactersLocalDataSource.fetchCharacterNextPage(page = nextPage).toCharacters()
                        .also { if (it.results.isNotEmpty()) { nextPage++}
                        }
                }
            )
        }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun getMultipleCharactersById(ids: List<Int>): Result<Characters> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharactersByStatus(status: String): Result<Characters> {
        TODO("Not yet implemented")
    }

}