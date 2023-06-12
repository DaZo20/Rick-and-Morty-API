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
        suspend fun getCharactersByName(name: String): Result<CharactersDto?>
        suspend fun getCharactersByStatus(status: String): Result<CharactersDto?>
        suspend fun getCharactersByGender(gender: String): Result<CharactersDto?>
        suspend fun getCharactersByStatusAndGender(status: String, gender: String): Result<CharactersDto?>

    }
    interface Local {
        suspend fun saveCharacterList(list: List<CharacterEntity>)
        suspend fun fetchCharacterList(): List<CharacterEntity>
        suspend fun fetchCharacterNextPage(page:Int): List<CharacterEntity>
        suspend fun fetchCharacterByName(name: String): List<CharacterEntity>
        suspend fun fetchCharactersByStatus(status: String): List<CharacterEntity>
        suspend fun fetchCharactersByGender(gender: String): List<CharacterEntity>
        suspend fun fetchCharactersByStatusAndGender(status: String, gender: String ): List<CharacterEntity>
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

    override suspend fun getCharactersByName(name: String): Result<CharactersDto?> =
        retrofitInstance.create(CharactersService::class.java).getCharactersByName(name = name)
            .runCatching { body() }

    override suspend fun getCharactersByStatus(status: String): Result<CharactersDto?> =
        retrofitInstance.create(CharactersService::class.java).getCharactersByStatus(status = status)
            .runCatching { body() }

    override suspend fun getCharactersByGender(gender: String): Result<CharactersDto?> =
        retrofitInstance.create(CharactersService::class.java).getCharactersByGender(gender = gender)
            .runCatching { body() }

    override suspend fun getCharactersByStatusAndGender(status: String, gender: String): Result<CharactersDto?> =
        retrofitInstance.create(CharactersService::class.java). getCharactersByStatusAndGender(status, gender)
            .runCatching { body() }


    override suspend fun saveCharacterList(list: List<CharacterEntity>) =
        roomDatabaseInstance.characterDao().insertAll(*list.toTypedArray())

    override suspend fun fetchCharacterList(): List<CharacterEntity> = roomDatabaseInstance.characterDao().getAll()

    override suspend fun fetchCharacterNextPage(page: Int): List<CharacterEntity> =
        roomDatabaseInstance.characterDao().getCharactersByPage(page = page)

    override suspend fun fetchCharacterByName(name: String): List<CharacterEntity> =
        roomDatabaseInstance.characterDao().getCharactersByName(name)

    override suspend fun fetchCharactersByStatus(status: String): List<CharacterEntity> =
        roomDatabaseInstance.characterDao().getCharactersByStatus(status)

    override suspend fun fetchCharactersByGender(gender: String): List<CharacterEntity> =
        roomDatabaseInstance.characterDao().getCharactersByGender(gender)

    override suspend fun fetchCharactersByStatusAndGender(gender: String, status: String): List<CharacterEntity> =
        roomDatabaseInstance.characterDao().getCharactersByStatusAndGender(status, gender)

}