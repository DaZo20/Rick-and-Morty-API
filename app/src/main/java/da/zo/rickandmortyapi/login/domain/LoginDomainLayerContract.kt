package da.zo.rickandmortyapi.login.domain

import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.domain.model.LoginUser


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