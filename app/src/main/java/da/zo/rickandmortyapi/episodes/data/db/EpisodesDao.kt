package da.zo.rickandmortyapi.episodes.data.db

import androidx.room.*
import da.zo.rickandmortyapi.characters.data.db.CharacterEntity
import da.zo.rickandmortyapi.episodes.domain.model.Episodes

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episode_table")
    suspend fun getAllEpisodes(): List<EpisodeEntity>

    @Query("SELECT * FROM episode_table WHERE page = :page")
    suspend fun getEpisodesByPage(page: Int): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg episodes: EpisodeEntity)

    @Delete
    suspend fun delete(episodes: EpisodeEntity)
}