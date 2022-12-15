package da.zo.rickandmortyapi.login.data.repository

import da.zo.rickandmortyapi.login.data.datasource.LoginDataSource
import da.zo.rickandmortyapi.login.domain.LoginDomainLayerContract
import da.zo.rickandmortyapi.login.domain.model.Email
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.domain.model.Password
import da.zo.rickandmortyapi.login.domain.utils.toLoginUser

object RickAndMortyLoginRepository : LoginDomainLayerContract.DataLayer.LoginRepository {

    lateinit var loginDataSource: LoginDataSource

    override suspend fun loginUser(email: Email, password: Password): Result<LoginUser> =
        try {
            loginDataSource.signInWithEmailAndPassword(email = email, password = password)
                .map { loginUserDto -> loginUserDto.toLoginUser() }
        } catch(e: Exception) {
            Result.failure(e)
        }

}