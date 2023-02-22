package da.zo.rickandmortyapi.episodes.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
@Entity(tableName = "episode_table")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val page: Int,
    val name: String,
    @ColumnInfo(name = "air_date") val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
