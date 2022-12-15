package da.zo.rickandmortyapi.login.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import da.zo.rickandmortyapi.login.data.model.LoginUserDto
import da.zo.rickandmortyapi.login.domain.model.Email
import da.zo.rickandmortyapi.login.domain.model.Password
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LoginDataSource {
    suspend fun signInWithEmailAndPassword(email: Email, password: Password): Result<LoginUserDto>
}

class FirebaseAuthDataSource @Inject constructor(
    private val fbAuthInstance: FirebaseAuth
) : LoginDataSource {


    override suspend fun signInWithEmailAndPassword(email: Email, password: Password): Result<LoginUserDto> =
        fbAuthInstance.signInWithEmailAndPassword(email.value, password.value).await()
            .runCatching { toLoginUserDto() }
}

fun AuthResult.toLoginUserDto(): LoginUserDto =
    LoginUserDto(
        name = user?.displayName,
        email = user?.email
    )