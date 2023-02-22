package da.zo.rickandmortyapi.register.data.repository

import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.domain.utils.toRegisterUser
import da.zo.rickandmortyapi.register.data.datasource.RegisterDataSource
import da.zo.rickandmortyapi.register.domain.RegisterDomainLayerContract
import da.zo.rickandmortyapi.register.domain.model.RegisterUser

object RickAndMortyRegisterRepository : RegisterDomainLayerContract.DataLayer.RegisterRepository {

    lateinit var registerDataSource: RegisterDataSource

    override suspend fun registerUser(email: Email, password: Password): Result<RegisterUser> =
        try {
            registerDataSource.createUserWithEmailAndPassword(email, password)
                .map { RegisterUserDto -> RegisterUserDto.toRegisterUser()}
        } catch (e: Exception){
            Result.failure(e)
        }

}