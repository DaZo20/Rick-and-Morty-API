package da.zo.rickandmortyapi.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.domain.LoginDomainLayerContract
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.domain.usecase.LoginUserWithEmailAndPassUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserWithEmailAndPassUC: LoginDomainLayerContract.PresentationLayer.UseCase<LoginUser>
) : ViewModel() {

    val loginUser: StateFlow<LoginUser?>
        get() = _loginUser.asStateFlow()
    private val _loginUser: MutableStateFlow<LoginUser?> = MutableStateFlow(null)

    fun onLoginOptionSelected(email: Email, password: Password) =
        viewModelScope.launch {
            loginUserWithEmailAndPassUC.invoke(email, password).onSuccess { user ->
                _loginUser.update { user }
            }.onFailure { th ->
                th.printStackTrace()
            }
        }

}