package da.zo.rickandmortyapi.login.domain.model

import da.zo.rickandmortyapi.common.utils.Email

data class LoginUser(
    val name: String,
    val email: Email
)

