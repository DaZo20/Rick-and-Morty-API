package da.zo.rickandmortyapi.register.domain.di

import da.zo.rickandmortyapi.register.domain.RegisterDomainLayerContract
import da.zo.rickandmortyapi.register.domain.model.RegisterUser
import da.zo.rickandmortyapi.register.domain.usecase.CreateUserWithEmailAndPassUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegisterDomainDependencies {

    @Provides
    fun providesRegisterUser(usecase: CreateUserWithEmailAndPassUC) : @JvmSuppressWildcards RegisterDomainLayerContract.PresentationLayer.UseCase<RegisterUser> = usecase

}