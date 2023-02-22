package da.zo.rickandmortyapi.episodes.data.model

import com.google.gson.annotations.SerializedName
import da.zo.rickandmortyapi.characters.data.model.InfoDto

data class MultipleEpisodesDto(
    val episodes: List<EpisodeDto>
)

data class EpisodesDto(
    val info: InfoDto,
    val results: List<EpisodeDto>
)

data class EpisodeDto(
    val id: Int,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)