package da.zo.rickandmortyapi.register.domain.model

import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password

data class RegisterUser(
    val name: String,
    val email: Email,
    val password: Password
)