package da.zo.rickandmortyapi.characters.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//
// Created by DaZo20 on 10/01/2023.
//
@Dao
interface CharactersDao {

    @Query("SELECT * FROM character_table")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character_table WHERE page = :page")
    suspend fun getCharactersByPage(page: Int): List<CharacterEntity>

    @Query("SELECT * FROM character_table WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg characters: CharacterEntity)

    @Delete
    suspend fun delete(characters: CharacterEntity)
}