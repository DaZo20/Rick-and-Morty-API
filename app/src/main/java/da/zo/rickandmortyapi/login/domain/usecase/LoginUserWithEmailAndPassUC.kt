package da.zo.rickandmortyapi.login.domain.usecase

import android.nfc.FormatException
import da.zo.rickandmortyapi.login.domain.LoginDomainLayerContract
import da.zo.rickandmortyapi.login.domain.model.Email
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.domain.model.Password
import da.zo.rickandmortyapi.login.domain.utils.isValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUserWithEmailAndPassUC @Inject constructor(
    private val loginRepository: LoginDomainLayerContract.DataLayer.LoginRepository
) : LoginDomainLayerContract.PresentationLayer.UseCase<LoginUser> {

    override suspend fun invoke(email: Email, password: Password): Result<LoginUser> =
        withContext(Dispatchers.IO) {
            if (email.isValid() && password.isValid()) {
                loginRepository.loginUser(email = email, password = password)
            } else {
                Result.failure(FormatException("E-mail or/and keypass are incorrect"))
            }
        }

}