package da.zo.rickandmortyapi.common.data.datasource

import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import da.zo.rickandmortyapi.MainActivity
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.data.datasource.LoginDataSource

import da.zo.rickandmortyapi.login.data.model.LoginUserDto
import da.zo.rickandmortyapi.register.data.datasource.RegisterDataSource
import da.zo.rickandmortyapi.register.data.model.RegisterUserDto
import da.zo.rickandmortyapi.register.presentation.view.RegisterActivity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val fbAuthInstance: FirebaseAuth
) : LoginDataSource, RegisterDataSource {

    override suspend fun signInWithEmailAndPassword(
        email: Email,
        password: Password
    ): Result<LoginUserDto> =
        fbAuthInstance.signInWithEmailAndPassword(email.value, password.value).await().runCatching { toLoginUserDto() }

    override suspend fun createUserWithEmailAndPassword(email: Email, password: Password
    ): Result<RegisterUserDto> =
        fbAuthInstance.createUserWithEmailAndPassword(email.value,password.value).await().runCatching { toRegisterUserDto() }


}

fun AuthResult.toLoginUserDto(): LoginUserDto =
    LoginUserDto(
        name = user?.displayName,
        email = user?.email
    )

fun AuthResult.toRegisterUserDto(): RegisterUserDto =
    RegisterUserDto(
        name = user?.displayName,
        email = user?.email,
    )