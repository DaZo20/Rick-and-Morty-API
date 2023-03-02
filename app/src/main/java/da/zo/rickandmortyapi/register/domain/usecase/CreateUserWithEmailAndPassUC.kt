package da.zo.rickandmortyapi.register.domain.usecase

import android.nfc.FormatException
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.databinding.ActivityRegisterBinding
import da.zo.rickandmortyapi.register.domain.RegisterDomainLayerContract
import da.zo.rickandmortyapi.register.domain.model.RegisterUser
import da.zo.rickandmortyapi.register.presentation.view.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserWithEmailAndPassUC @Inject constructor(
    private val registerRepository: RegisterDomainLayerContract.DataLayer.RegisterRepository
) : RegisterDomainLayerContract.PresentationLayer.UseCase<RegisterUser> {

    private var registerBinding: ActivityRegisterBinding? = null

    override suspend fun invoke(email: Email, password: Password): Result<RegisterUser> =
        withContext(Dispatchers.IO) {
            if (registerBinding?.emailEt?.text.toString().isNotEmpty() && registerBinding?.userEt?.text.toString().isNotEmpty()) {
                if (registerBinding?.registerPasswordEt?.text.toString() == registerBinding?.registerConfirmPasswordEt?.text.toString()) {
                    registerRepository.registerUser(email, password)
                } else {
                    Result.failure(FormatException("Passwords must match"))
                }
            } else {
                Result.failure(FormatException("There cannot be any empty fields"))
            }
        }


}