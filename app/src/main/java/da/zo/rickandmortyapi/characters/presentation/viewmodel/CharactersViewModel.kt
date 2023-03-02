package da.zo.rickandmortyapi.characters.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import da.zo.rickandmortyapi.characters.data.api.CharactersService
import da.zo.rickandmortyapi.characters.data.datasource.CharactersDataSource
import da.zo.rickandmortyapi.characters.data.db.CharactersDao
import da.zo.rickandmortyapi.characters.data.repository.RickAndMortyCharacterRepository
import da.zo.rickandmortyapi.characters.data.repository.RickAndMortyCharacterRepository.charactersLocalDataSource
import da.zo.rickandmortyapi.characters.data.utils.toCharacter
import da.zo.rickandmortyapi.characters.data.utils.toCharacters
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.domain.model.Character
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByNameUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Named

//
// Created by DaZo20 on 10/01/2023.
//

class CharactersViewModel @Inject constructor(
    @Named("get_all_characters") val getAllCharactersUc: CharacterDomainLayerContract.PresentationLayer.UseCase<Characters>,
    @Named("get_characters_next_page") val getCharactersNextPageUc: CharacterDomainLayerContract.PresentationLayer.UseCase<Characters>,
    @Named("get_characters_by_name") val getCharactersByNameUC: CharacterDomainLayerContract.PresentationLayer.UseCase<Characters>
) : ViewModel() {

    var filterValue = MutableLiveData<Array<Int>>(emptyArray())
    val characters: StateFlow<Characters?>
        get() = _characters.asStateFlow()

    private var _characters: MutableStateFlow<Characters?> = MutableStateFlow(null)

    init {
        fetchCharactersData()
        filterValue.value = arrayOf(0, 0)
    }

    private fun fetchCharactersData() {
        viewModelScope.launch {
            getAllCharactersUc().onSuccess { characters ->
                _characters.update { characters }
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun onEndOfScrollReached() {
        viewModelScope.launch {
            getCharactersNextPageUc().onSuccess { characters ->
                _characters.value = characters
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun fetchCharactersByName(name: String) {
        viewModelScope.launch {
            var search = RickAndMortyCharacterRepository.getCharactersByName(name)
            _characters.value = search.getOrNull()
        }
    }

    fun fetchCharactersByStatus(status: String) {
        viewModelScope.launch {
            val filter = RickAndMortyCharacterRepository.getCharactersByStatus(status)
            _characters.value = filter.getOrNull()
        }

    }

    fun fetchCharactersByGender(gender: String) {
        viewModelScope.launch {
            val filter = RickAndMortyCharacterRepository.getCharactersByGender(gender)
            _characters.value = filter.getOrNull()
        }
    }

    fun fetchCharactersByStatusAndGender(status: String, gender: String) {
        viewModelScope.launch {
            val filter =
                RickAndMortyCharacterRepository.getCharactersByStatusAndGender(status, gender)
            _characters.value = filter.getOrNull()
        }
    }

}