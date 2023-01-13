package da.zo.rickandmortyapi.common.utils

import da.zo.rickandmortyapi.characters.data.db.LocationEntity
import da.zo.rickandmortyapi.characters.data.db.OriginEntity
import da.zo.rickandmortyapi.characters.data.model.LocationDto
import da.zo.rickandmortyapi.characters.data.model.OriginDto
import da.zo.rickandmortyapi.characters.domain.model.Location
import da.zo.rickandmortyapi.characters.domain.model.Origin

//
// Created by DaZo20 on 13/01/2023.
//
fun OriginDto.toBo() : Origin = Origin(name = name, url = url)

fun LocationDto.toBo() : Location = Location(name = name, url = url)

fun OriginDto.toEntity(): OriginEntity =
    OriginEntity(
        name = name,
        url = url
    )

fun LocationDto.toEntity(): LocationEntity =
    LocationEntity(
        name = name,
        url = url
    )

fun OriginEntity.toBo(): Origin =
    Origin(
        name = name,
        url = url
    )

fun LocationEntity.toBo(): Location =
    Location(
        name = name,
        url = url
    )