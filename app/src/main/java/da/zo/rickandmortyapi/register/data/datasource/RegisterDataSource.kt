package da.zo.rickandmortyapi.register.data.datasource

import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.register.data.model.RegisterUserDto

interface RegisterDataSource {
    suspend fun createUserWithEmailAndPassword(email: Email, password: Password): Result<RegisterUserDto>
}