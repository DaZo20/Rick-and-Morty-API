package da.zo.rickandmortyapi.login.domain.utils

import da.zo.rickandmortyapi.login.data.model.LoginUserDto
import da.zo.rickandmortyapi.login.domain.model.Email
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.domain.model.Password

private const val DEFAULT_STRING_VALUE = ""

fun Email.isValid(): Boolean {
    val pattern = """.+@.+\.com""".toRegex()
    return value.trim().contains(pattern)
}
private const val KEYPASS_LENGTH: Int = 5
fun Password.isValid(): Boolean = value.trim().length > KEYPASS_LENGTH

fun LoginUserDto.toLoginUser() =
    LoginUser(
        name = name ?: DEFAULT_STRING_VALUE,
        email = Email(value =  email ?: DEFAULT_STRING_VALUE)
    )