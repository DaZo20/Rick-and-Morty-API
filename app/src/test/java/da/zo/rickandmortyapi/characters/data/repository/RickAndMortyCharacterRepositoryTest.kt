package da.zo.rickandmortyapi.characters.data.repository

import da.zo.rickandmortyapi.characters.data.datasource.CharactersDataSource
import da.zo.rickandmortyapi.characters.data.model.*
import da.zo.rickandmortyapi.characters.domain.model.Characters
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.exceptions.base.MockitoException

//
// Created by DaZo20 on 21/02/2023.
//

private const val DEFAULT_INT_VALUE = 0
private const val DEFAULT_STRING_VALUE = ""

class RickAndMortyCharacterRepositoryTest {
    lateinit var sut: RickAndMortyCharacterRepository

    @Before
    fun setUp() {
        sut = RickAndMortyCharacterRepository.apply {
            charactersRemoteDataSource = mock(CharactersDataSource.Remote::class.java)
            charactersLocalDataSource = mock(CharactersDataSource.Local::class.java)
        }
    }

//    @After
//    fun tearDown() {
//    }

    @Test
    fun `When all characters are requested, and the API response is successful but 'null', the database is queried`() = runTest {
        //Given
        `when`(sut.charactersRemoteDataSource.getAllCharactersListResponse()).thenReturn(getDummyCharactersDtoNullResult())
        `when`(sut.charactersLocalDataSource.fetchCharacterList()).thenReturn(anyList())
        //When
        val result: Result<Characters> = sut.getAllCharactersList()
        //Then
        verify(sut.charactersLocalDataSource).fetchCharacterList()
    }

    @Test
    fun `When all characters are requested, and the API response triggers an 'Exception', the database is queried`() = runTest {
        //Given
        `when`(sut.charactersRemoteDataSource.getAllCharactersListResponse()).thenThrow(MockitoException::class.java)
        `when`(sut.charactersLocalDataSource.fetchCharacterList()).thenReturn(anyList())
        //When
        val result: Result<Characters> = sut.getAllCharactersList()
        //Then
        verify(sut.charactersLocalDataSource).fetchCharacterList()
    }

    @Test
    fun `When all characters are requested, and the API response successful, a list of 'Characters' is returned`() = runTest {
        //Given
        `when`(sut.charactersRemoteDataSource.getAllCharactersListResponse()).thenReturn(getDummyCharactersDtoResult())
        //When
        val result: Result<Characters> = sut.getAllCharactersList()
        //Then
            //State test
        Assert.assertTrue(result.isSuccess && result.getOrNull()?.results?.isNotEmpty() == true)
            //Behaviour test
        verify(sut.charactersLocalDataSource).saveCharacterList(list = anyList())
    }

    private fun getDummyCharactersDtoNullResult(): Result<CharactersDto?> =
        Result.success(null)

    private fun getDummyCharactersDtoResult(): Result<CharactersDto?> =
        Result.success(getDummyCharactersDto())

    private fun getDummyCharactersDto(): CharactersDto =
        CharactersDto(
            info = getDummyInfoDto(),
            results = getDummyCharacterDtoList()
        )
    private fun getDummyCharacterDtoList(): List<CharacterDto> = listOf(getDummyCharacterDto())

    private fun getDummyCharacterDto(): CharacterDto =
        CharacterDto(
            id = DEFAULT_INT_VALUE,
            name = DEFAULT_STRING_VALUE,
            status = DEFAULT_STRING_VALUE,
            species = DEFAULT_STRING_VALUE,
            type = DEFAULT_STRING_VALUE,
            gender = DEFAULT_STRING_VALUE,
            origin = getDummyOriginDto(),
            location = getDummyLocationDto(),
            image = DEFAULT_STRING_VALUE,
            episode = emptyList(),
            url = DEFAULT_STRING_VALUE,
            created = DEFAULT_STRING_VALUE
        )

    private fun getDummyOriginDto(): OriginDto =
        OriginDto(
            name = DEFAULT_STRING_VALUE,
            url = DEFAULT_STRING_VALUE
        )

    private fun getDummyInfoDto(): InfoDto =
        InfoDto(
            count = DEFAULT_INT_VALUE,
            pages = DEFAULT_INT_VALUE,
            next = DEFAULT_STRING_VALUE,
            prev = DEFAULT_STRING_VALUE
        )

    private fun getDummyLocationDto(): LocationDto =
        LocationDto(
            name = DEFAULT_STRING_VALUE,
            url = DEFAULT_STRING_VALUE
        )
}