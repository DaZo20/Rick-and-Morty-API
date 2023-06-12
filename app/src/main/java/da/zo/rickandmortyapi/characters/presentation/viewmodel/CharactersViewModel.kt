package da.zo.rickandmortyapi.characters.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import da.zo.rickandmortyapi.characters.data.repository.RickAndMortyCharacterRepository
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByGenderUc
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByNameUC
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByStatusAndGenderUc
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByStatusUc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

//
// Created by DaZo20 on 10/01/2023.
//

class CharactersViewModel @Inject constructor(
    @Named("get_all_characters") val getAllCharactersUc: CharacterDomainLayerContract.PresentationLayer.UseCase<Characters>,
    @Named("get_characters_next_page") val getCharactersNextPageUc: CharacterDomainLayerContract.PresentationLayer.UseCase<Characters>,
    @Named("get_characters_by_name") val getCharactersByNameUC: GetCharactersByNameUC,
    @Named("get_characters_by_gender") val getCharactersByGenderUC: GetCharactersByGenderUc,
    @Named("get_characters_by_status") val getCharactersByStatusUC: GetCharactersByStatusUc,
    @Named("get_characters_by_gender_and_status") val getCharactersByStatusAndGenderUC: GetCharactersByStatusAndGenderUc
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
            getCharactersByNameUC(name).onSuccess { characters ->
                _characters.value = characters
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun fetchCharactersByStatus(status: String) {
        viewModelScope.launch {
            getCharactersByStatusUC(status).onSuccess { characters ->
                _characters.value = characters
            }.onFailure { err ->
                err.printStackTrace()
            }
        }

    }

    fun fetchCharactersByGender(gender: String) {
        viewModelScope.launch {
            getCharactersByGenderUC(gender).onSuccess { characters ->
                _characters.value = characters
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

    fun fetchCharactersByStatusAndGender(status: String, gender: String) {
        viewModelScope.launch {
            getCharactersByStatusAndGenderUC(status, gender).onSuccess { characters ->
                _characters.value = characters
            }.onFailure { err ->
                err.printStackTrace()
            }
        }
    }

}