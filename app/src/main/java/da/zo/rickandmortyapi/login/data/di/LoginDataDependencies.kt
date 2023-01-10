package da.zo.rickandmortyapi.login.data.di

import da.zo.rickandmortyapi.common.data.datasource.FirebaseAuthDataSource
import da.zo.rickandmortyapi.login.data.datasource.LoginDataSource
import da.zo.rickandmortyapi.login.data.repository.RickAndMortyLoginRepository
import da.zo.rickandmortyapi.login.domain.LoginDomainLayerContract
import da.zo.rickandmortyapi.register.data.datasource.RegisterDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginDataDependencies {

    @Provides
    fun providesLoginRepository(
        loginDataSource: LoginDataSource
    ) : LoginDomainLayerContract.DataLayer.LoginRepository = RickAndMortyLoginRepository.apply {
        this.loginDataSource = loginDataSource
    }

    @Provides
    fun providesLoginDataSource( firebaseDataSource: FirebaseAuthDataSource) : LoginDataSource = firebaseDataSource

}