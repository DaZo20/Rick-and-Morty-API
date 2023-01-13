package da.zo.rickandmortyapi.characters.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created by DaZo20 on 10/01/2023.
//

/*@Entity
data class TestEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val info: String,
    val results: String
)*/

@Entity
data class CharactersEntity(
    @PrimaryKey val uid: Int,
    val info: InfoEntity,
    val results: List<CharacterEntity>
)

@Entity
data class InfoEntity(
    @PrimaryKey val uid: Int,
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: Any
)

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val page: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @Embedded val origin: OriginEntity,
    @Embedded val location: LocationEntity,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class OriginEntity(
    @ColumnInfo(name = "origin_name") val name: String,
    @ColumnInfo(name = "origin_url") val url: String
)

data class LocationEntity(
    @ColumnInfo(name = "location_name") val name: String,
    @ColumnInfo(name = "location_url") val url: String
)