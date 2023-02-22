package da.zo.rickandmortyapi.episodes.data.repository

import da.zo.rickandmortyapi.characters.data.model.InfoDto
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.episodes.data.datasource.EpisodesDataSource
import da.zo.rickandmortyapi.episodes.data.model.EpisodeDto
import da.zo.rickandmortyapi.episodes.data.model.EpisodesDto
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.exceptions.base.MockitoException

//
// Created by DaZo20 on 22/02/2023.
//

private const val DEFAULT_INT_VALUE = 0
private const val DEFAULT_STRING_VALUE = ""

class RickAndMortyEpisodeRepositoryTest {

    lateinit var sut: RickAndMortyEpisodeRepository

    @Before
    fun setUp() {
        sut = RickAndMortyEpisodeRepository.apply {
            episodesRemoteDataSource = mock(EpisodesDataSource.Remote::class.java)
            episodesLocalDataSource = mock(EpisodesDataSource.Local::class.java)
        }
    }

    @Test
    fun `When all episodes are requested, and the API response is successful but 'null', the database is queried`() = runTest {
        //Given
        `when`(sut.episodesRemoteDataSource.getAllEpisodesListResponse()).thenReturn(getDummyEpisodesDtoNullResult())
        `when`(sut.episodesLocalDataSource.fetchEpisodesList()).thenReturn(anyList())
        //When
        val result: Result<Episodes> = sut.getAllEpisodesList()
        //Then
        verify(sut.episodesLocalDataSource).fetchEpisodesList()
    }

    @Test
    fun `When all episodes are requested, and the API response successful, a list of 'Episodes' is returned`() = runTest {
        //Given
        `when`(sut.episodesRemoteDataSource.getAllEpisodesListResponse()).thenReturn(getDummyEpisodesDtoResult())
        //When
        val result: Result<Episodes> = sut.getAllEpisodesList()
        //Then
            //State test
        Assert.assertTrue(result.isSuccess && result.getOrNull()?.results?.isNotEmpty() == true)
            //Behaviour test
        verify(sut.episodesLocalDataSource).saveEpisodesList(list = anyList())
    }

    @Test
    fun `When all characters are requested, and the API response triggers an 'Exception', the database is queried`() = runTest {
        //Given
        `when`(sut.episodesRemoteDataSource.getAllEpisodesListResponse()).thenThrow(MockitoException::class.java)
        `when`(sut.episodesLocalDataSource.fetchEpisodesList()).thenReturn(anyList())
        //When
        val result: Result<Episodes> = sut.getAllEpisodesList()
        //Then
        verify(sut.episodesLocalDataSource).fetchEpisodesList()
    }

    private fun getDummyEpisodesDtoNullResult(): Result<EpisodesDto?> =
        Result.success(null)

    private fun getDummyEpisodesDtoResult(): Result<EpisodesDto?> =
        Result.success(getDummyEpisodesDto())

    private fun getDummyEpisodesDto(): EpisodesDto =
        EpisodesDto(
            info = getDummyInfoDto(),
            results = getDummyEpisodeDtoList()
        )

    private fun getDummyEpisodeDtoList(): List<EpisodeDto> = listOf(getDummyEpisodeDto())

    private fun getDummyEpisodeDto(): EpisodeDto =
        EpisodeDto(
            id = DEFAULT_INT_VALUE,
            episode = DEFAULT_STRING_VALUE,
            url = DEFAULT_STRING_VALUE,
            name = DEFAULT_STRING_VALUE,
            created = DEFAULT_STRING_VALUE,
            airDate = DEFAULT_STRING_VALUE,
            characters = emptyList()
        )

    private fun getDummyInfoDto(): InfoDto =
        InfoDto(
            pages = DEFAULT_INT_VALUE,
            prev = DEFAULT_STRING_VALUE,
            next = DEFAULT_STRING_VALUE,
            count = DEFAULT_INT_VALUE
        )
}