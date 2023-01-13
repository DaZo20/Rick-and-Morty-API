package da.zo.rickandmortyapi.characters.data.di

import da.zo.rickandmortyapi.characters.data.datasource.CharactersDataSource
import da.zo.rickandmortyapi.characters.data.datasource.RickAndMortyCharacterDataSource
import da.zo.rickandmortyapi.characters.data.repository.RickAndMortyCharacterRepository
import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

//
// Created by DaZo20 on 10/01/2023.
//
@Module
@InstallIn(ActivityComponent::class)
object CharacterDataModule {

    @Provides
    fun providesCharactersLocalDataSource(charactersDatasource: RickAndMortyCharacterDataSource): CharactersDataSource.Local = charactersDatasource
    @Provides
    fun providesCharactersRemoteDataSource(charactersDatasource: RickAndMortyCharacterDataSource): CharactersDataSource.Remote =  charactersDatasource

    @Provides
    fun provideCharacterRemoteRepository(
        characterDataSource: CharactersDataSource.Remote,
        localDataSource: CharactersDataSource.Local
    ): CharacterDomainLayerContract.DataLayer.CharacterRepository =
        RickAndMortyCharacterRepository.apply{
            charactersRemoteDataSource = characterDataSource
            charactersLocalDataSource = localDataSource
        }
}