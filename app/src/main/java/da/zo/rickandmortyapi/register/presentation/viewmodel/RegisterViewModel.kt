package da.zo.rickandmortyapi.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.register.domain.RegisterDomainLayerContract
import da.zo.rickandmortyapi.register.domain.model.RegisterUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserWithEmailAndPassUC: RegisterDomainLayerContract.PresentationLayer.UseCase<RegisterUser>
) :   ViewModel() {
    val registerUser: StateFlow<RegisterUser?>
        get() = _registerUser.asStateFlow()
    private val _registerUser: MutableStateFlow<RegisterUser?> = MutableStateFlow(null)

    fun onRegisterOptionSelected(email: Email, password: Password) =
        viewModelScope.launch {
            registerUserWithEmailAndPassUC.invoke(email, password).onSuccess { user ->
                _registerUser.update { user }
            }.onFailure { th ->
                th.printStackTrace()
            }
        }
}