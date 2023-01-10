package da.zo.rickandmortyapi.register.domain

import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.register.domain.model.RegisterUser

interface RegisterDomainLayerContract {
    interface PresentationLayer {
        interface UseCase<T>{
            suspend operator fun invoke(email: Email, password: Password): Result<T>
        }
    }

    interface DataLayer {
        interface RegisterRepository {
            suspend fun registerUser(email: Email, password: Password): Result<RegisterUser>
        }
    }
}