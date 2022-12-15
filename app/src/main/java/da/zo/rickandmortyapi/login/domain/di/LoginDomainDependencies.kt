package da.zo.rickandmortyapi.login.domain.di

import da.zo.rickandmortyapi.login.domain.LoginDomainLayerContract
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.domain.usecase.LoginUserWithEmailAndPassUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginDomainDependencies {

    @Provides
    fun providesLoginUser(usecase: LoginUserWithEmailAndPassUC) : @JvmSuppressWildcards LoginDomainLayerContract.PresentationLayer.UseCase<LoginUser> = usecase

}