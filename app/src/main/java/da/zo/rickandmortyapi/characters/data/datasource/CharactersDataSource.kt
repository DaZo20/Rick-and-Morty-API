package da.zo.rickandmortyapi.characters.data.datasource

import da.zo.rickandmortyapi.characters.data.api.CharactersService
import da.zo.rickandmortyapi.characters.data.db.CharacterEntity
import da.zo.rickandmortyapi.characters.data.model.CharactersDto
import da.zo.rickandmortyapi.characters.domain.model.Character
import da.zo.rickandmortyapi.common.db.ApplicationDatabase
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

//
// Created by DaZo20 on 10/01/2023.
//

interface CharactersDataSource {

    interface Remote {
        suspend fun getAllCharactersListResponse(): Result<CharactersDto?>
        suspend fun getCharactersNextPage(page: Int): Result<CharactersDto?>
        suspend fun getCharactersById(id: Int): Result<Character?>
    }
    interface Local {
        suspend fun saveCharacterList(list: List<CharacterEntity>)
        suspend fun fetchCharacterList(): List<CharacterEntity>
        suspend fun fetchCharacterNextPage(page:Int): List<CharacterEntity>
        suspend fun fetchCharacterById(id: Int): CharacterEntity
    }

}

class RickAndMortyCharacterDataSource @Inject constructor(
    private val retrofitInstance: Retrofit,
    private val roomDatabaseInstance: ApplicationDatabase
) : CharactersDataSource.Remote, CharactersDataSource.Local {


    override suspend fun getAllCharactersListResponse(): Result<CharactersDto?> =
        retrofitInstance.create(CharactersService::class.java).getAllCharactersList()
            .runCatching { body() }

    override suspend fun getCharactersNextPage(page: Int): Result<CharactersDto?> =
        retrofitInstance.create(CharactersService::class.java).getAllCharactersList(page = page)
            .runCatching { body() }

    override suspend fun getCharactersById(id: Int): Result<Character?> =
        retrofitInstance.create(CharactersService::class.java).getSingleCharacter(id = id)
            .runCatching { body() }


    override suspend fun saveCharacterList(list: List<CharacterEntity>) =
        roomDatabaseInstance.characterDao().insertAll(*list.toTypedArray())


    override suspend fun fetchCharacterList(): List<CharacterEntity> = roomDatabaseInstance.characterDao().getAll()

    override suspend fun fetchCharacterNextPage(page: Int): List<CharacterEntity> =
        roomDatabaseInstance.characterDao().getCharactersByPage(page = page)

    override suspend fun fetchCharacterById(id: Int): CharacterEntity =
        roomDatabaseInstance.characterDao().getCharacterById(id = id)
}