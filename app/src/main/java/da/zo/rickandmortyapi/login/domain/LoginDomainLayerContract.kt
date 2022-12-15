package da.zo.rickandmortyapi.login.domain

import da.zo.rickandmortyapi.login.domain.model.Email
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.domain.model.Password

interface LoginDomainLayerContract {
    interface PresentationLayer {
        interface UseCase<T>{
            suspend operator fun invoke(email: Email, password: Password): Result<T>
        }
    }

    interface DataLayer {
        interface LoginRepository {
            suspend fun loginUser(email: Email, password: Password): Result<LoginUser>
        }
    }
}