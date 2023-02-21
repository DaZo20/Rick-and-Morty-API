package da.zo.rickandmortyapi.characters.data.utils

import da.zo.rickandmortyapi.characters.data.db.CharacterEntity
import da.zo.rickandmortyapi.characters.data.db.LocationEntity
import da.zo.rickandmortyapi.characters.data.db.OriginEntity
import da.zo.rickandmortyapi.characters.data.model.CharacterDto
import da.zo.rickandmortyapi.characters.data.model.CharactersDto
import da.zo.rickandmortyapi.characters.data.model.LocationDto
import da.zo.rickandmortyapi.characters.data.model.OriginDto
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.domain.model.Character
import da.zo.rickandmortyapi.characters.domain.model.Location
import da.zo.rickandmortyapi.characters.domain.model.Origin

//
// Created by DaZo20 on 11/01/2023.
//

const val DEFAULT_INT = 1
const val DEFAULT_STRING = ""
 val DEFAULT_LIST_STRING: List<String> = listOf("")

fun CharactersDto?.toCharacters() : Characters =
    Characters(results = this?.results?.toCharacterList() ?: emptyList())
fun Character?.toCharacter() : Character =
    Character(
        id = this?.id ?: DEFAULT_INT,
        name = this?.name ?: DEFAULT_STRING,
        status = this?.status ?: DEFAULT_STRING,
        species = this?.species ?: DEFAULT_STRING,
        type = this?.type ?: DEFAULT_STRING,
        gender = this?.gender ?: DEFAULT_STRING,
        origin =  Origin(name = DEFAULT_STRING,url = DEFAULT_STRING),
        location = Location(name = DEFAULT_STRING, url = DEFAULT_STRING),
        image = this?.image ?: DEFAULT_STRING,
        episode = this?.episode ?: DEFAULT_LIST_STRING,
        url = this?.url ?: DEFAULT_STRING,
        created = this?.created ?: DEFAULT_STRING
    )

fun CharacterEntity.toCharacter() : Character =
    Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toBo(),
        location = location.toBo(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )

fun List<CharacterDto>.toCharacterList() : List<Character> =
    this.map { dto ->
        with(dto) {
            Character(
                id = id,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                origin = origin.toBo(),
                location = location.toBo(),
                image = image,
                episode = episode,
                url = url,
                created = created
            )
        }
    }
 fun List<Character>.toCharacter(): Character =
     Character(
         id = DEFAULT_INT,
         name = DEFAULT_STRING,
         status = DEFAULT_STRING,
         species = DEFAULT_STRING,
         type = DEFAULT_STRING,
         gender = DEFAULT_STRING,
         origin = Origin(name = DEFAULT_STRING, url = DEFAULT_STRING),
         location = Location(name = DEFAULT_STRING, url = DEFAULT_STRING),
         image = DEFAULT_STRING,
         episode = listOf(DEFAULT_STRING),
         url =DEFAULT_STRING ,
         created = DEFAULT_STRING
     )

fun OriginDto.toBo() : Origin = Origin(name = name, url = url)
fun LocationDto.toBo() : Location = Location(name = name, url = url)


fun CharactersDto.toCharactersEntity(page: Int): List<CharacterEntity> = results.map { dto ->
    with(dto) {
        CharacterEntity(
            id = id,
            page = page,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin.toEntity(),
            location = location.toEntity(),
            image = image,
            episode = episode,
            url = url,
            created = created
        )
    }
}

private fun OriginDto.toEntity(): OriginEntity =
    OriginEntity(
        name = name,
        url = url
    )

private fun LocationDto.toEntity(): LocationEntity =
    LocationEntity(
        name = name,
        url = url
    )

fun List<CharacterEntity>.toCharacters(): Characters =
    Characters(
        results = this.map { entity ->
            with(entity){
                Character(
                    id = id,
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender,
                    origin = origin.toBo(),
                    location = location.toBo(),
                    image = image,
                    episode = episode,
                    url = url,
                    created = created
                )
            }

        }
    )

private fun OriginEntity.toBo(): Origin =
    Origin(
        name = name,
        url = url
    )

private fun LocationEntity.toBo():Location =
    Location(
        name = name,
        url = url
    )

