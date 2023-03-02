package da.zo.rickandmortyapi.characters.data.repository

import da.zo.rickandmortyapi.characters.data.datasource.CharactersDataSource
import da.zo.rickandmortyapi.characters.data.utils.toCharacter
import da.zo.rickandmortyapi.characters.data.utils.toCharacters
import da.zo.rickandmortyapi.characters.data.utils.toCharactersEntity
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Character
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.databinding.FragmentCharacterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

//
// Created by DaZo20 on 11/01/2023.
//

@Singleton
object RickAndMortyCharacterRepository :
    CharacterDomainLayerContract.DataLayer.CharacterRepository {

    private var nextPage: Int = 1

    //    private val name: String = ""
    lateinit var charactersRemoteDataSource: CharactersDataSource.Remote
    lateinit var charactersLocalDataSource: CharactersDataSource.Local


    override suspend fun getAllCharactersList(): Result<Characters> =
        try {
            charactersRemoteDataSource.getAllCharactersListResponse().map { dto ->
                dto?.toCharacters()?.also {
                    withContext(Dispatchers.IO) {
                        charactersLocalDataSource.saveCharacterList(
                            list = dto.toCharactersEntity(
                                page = nextPage
                            )
                        )
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
                        charactersLocalDataSource.saveCharacterList(
                            list = dto.toCharactersEntity(
                                page = nextPage
                            )
                        )
                        nextPage++
                    }
                } ?: run {
                    withContext(Dispatchers.IO) {
                        charactersLocalDataSource.fetchCharacterNextPage(page = nextPage)
                            .toCharacters()
                            .also {
                                if (it.results.isNotEmpty()) {
                                    nextPage++
                                }
                            }
                    }
                }
            }
        } catch (e: Exception) {
            Result.success(
                withContext(Dispatchers.IO) {
                    charactersLocalDataSource.fetchCharacterNextPage(page = nextPage).toCharacters()
                        .also {
                            if (it.results.isNotEmpty()) {
                                nextPage++
                            }
                        }
                }
            )
        }

    override suspend fun getCharactersByName(name: String): Result<Characters> =
        try {
            withContext(Dispatchers.IO) {
                charactersRemoteDataSource.getCharactersByName(name).map { dto ->
                    dto.toCharacters()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                Result.success(charactersLocalDataSource.fetchCharacterByName(name).toCharacters())
            }
        }

    override suspend fun getCharactersByStatus(status: String): Result<Characters> =
        try {
            withContext(Dispatchers.IO) {
                charactersRemoteDataSource.getCharactersByStatus(status).map { dto ->
                    dto.toCharacters()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                Result.success(
                    charactersLocalDataSource.fetchCharactersByStatus(status).toCharacters()
                )
            }
        }

    override suspend fun getCharactersByGender(gender: String): Result<Characters> =
        try {
            withContext(Dispatchers.IO) {
                charactersRemoteDataSource.getCharactersByGender(gender).map{ dto ->
                    dto.toCharacters()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                Result.success(charactersLocalDataSource.fetchCharactersByGender(gender).toCharacters())
            }
        }

    override suspend fun getCharactersByStatusAndGender(
        status: String,
        gender: String
    ): Result<Characters> =
        try {
            withContext(Dispatchers.IO) {
                charactersRemoteDataSource.getCharactersByStatusAndGender(status, gender).map { dto ->
                    dto.toCharacters()
                }
            }
        }catch (e: Exception) {
            withContext(Dispatchers.IO) {
                Result.success(charactersLocalDataSource.fetchCharactersByStatusAndGender(status, gender).toCharacters())
            }
        }


}