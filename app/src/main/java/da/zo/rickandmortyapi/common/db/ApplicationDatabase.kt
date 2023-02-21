package da.zo.rickandmortyapi.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import da.zo.rickandmortyapi.characters.data.db.CharacterEntity
import da.zo.rickandmortyapi.characters.data.db.CharactersDao

//
// Created by DaZo20 on 10/01/2023.
//

@Database(
    //entities = [CharactersEntity::class, CharacterEntity::class, InfoEntity::class, OriginEntity::class, LocationEntity::class], version = 1
    entities = [CharacterEntity::class/*, EpisodeEntity::class*/], version = 1
)
@TypeConverters(ReferenceListConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun characterDao(): CharactersDao
   // abstract fun episodeDao(): EpisodesDao
}