package da.zo.rickandmortyapi.register.data.di

import da.zo.rickandmortyapi.common.data.datasource.FirebaseAuthDataSource
import da.zo.rickandmortyapi.register.data.datasource.RegisterDataSource
import da.zo.rickandmortyapi.register.data.repository.RickAndMortyRegisterRepository
import da.zo.rickandmortyapi.register.domain.RegisterDomainLayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegisterDataDependencies {

    @Provides
    fun providesRegisterRepository(
        registerDataSource: RegisterDataSource
    )  : RegisterDomainLayerContract.DataLayer.RegisterRepository = RickAndMortyRegisterRepository.apply {
        this.registerDataSource = registerDataSource
    }

    @Provides
    fun providesRegisterDataSource(firebaseDataSource: FirebaseAuthDataSource) : RegisterDataSource = firebaseDataSource

}