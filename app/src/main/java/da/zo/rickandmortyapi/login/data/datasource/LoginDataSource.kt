package da.zo.rickandmortyapi.login.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.data.model.LoginUserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LoginDataSource {
    suspend fun signInWithEmailAndPassword(email: Email, password: Password): Result<LoginUserDto>
}



