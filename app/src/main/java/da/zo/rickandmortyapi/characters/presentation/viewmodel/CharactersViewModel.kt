package da.zo.rickandmortyapi.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import dagger.hilt.android.lifecycle.HiltViewModel
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
    @Named("get_characters_next_page") val getCharactersNextPageUc: CharacterDomainLayerContract.PresentationLayer.UseCase<Characters>
) : ViewModel() {

    val characters: StateFlow<Characters?>
        get() = _characters.asStateFlow()

    private var _characters: MutableStateFlow<Characters?> = MutableStateFlow(null)

    init {
        fetchCharactersData()
    }

    private fun fetchCharactersData() {
        viewModelScope.launch {
            getAllCharactersUc().onSuccess { characters ->
                _characters.update { characters }
            }.onFailure { ch ->
                ch.printStackTrace()
            }
        }
    }

    fun onEndOfScrollReached(){
        viewModelScope.launch {
            getCharactersNextPageUc().onSuccess { characters ->
                _characters.value = characters
            }.onFailure { ch ->
                ch.printStackTrace()
            }
        }
    }

}