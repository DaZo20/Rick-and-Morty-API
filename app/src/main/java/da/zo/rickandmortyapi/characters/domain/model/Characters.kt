package da.zo.rickandmortyapi.characters.domain.model

//
// Created by DaZo20 on 11/01/2023.
//

data class MultipleCharacters(
    val characters: List<Character>
)
data class Characters(
    val results: List<Character>
)
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
data class Origin(
    val name: String,
    val url: String
)
data class Location(
    val name: String,
    val url: String
)