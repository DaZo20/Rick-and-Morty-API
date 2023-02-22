package da.zo.rickandmortyapi.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import da.zo.rickandmortyapi.characters.data.db.CharacterEntity
import da.zo.rickandmortyapi.characters.data.db.CharactersDao
import da.zo.rickandmortyapi.episodes.data.db.EpisodeEntity
import da.zo.rickandmortyapi.episodes.data.db.EpisodesDao

//
// Created by DaZo20 on 10/01/2023.
//

@Database(
    entities = [CharacterEntity::class, EpisodeEntity::class], version = 2
)
@TypeConverters(ReferenceListConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun characterDao(): CharactersDao
    abstract fun episodeDao(): EpisodesDao
}