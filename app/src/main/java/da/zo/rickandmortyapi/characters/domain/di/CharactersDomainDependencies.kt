package da.zo.rickandmortyapi.characters.domain.di

import da.zo.rickandmortyapi.characters.domain.CharacterDomainLayerContract
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.domain.model.Character
import da.zo.rickandmortyapi.characters.domain.usecase.GetAllCharactersUc
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharacterNextPageUc
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByGenderUc
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByNameUC
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByStatusAndGenderUc
import da.zo.rickandmortyapi.characters.domain.usecase.GetCharactersByStatusUc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

//
// Created by DaZo20 on 13/01/2023.
//

@Module
@InstallIn(SingletonComponent::class)
class CharactersDomainDependencies {

    @Provides
    @Named("get_all_characters")
    fun providesGetAllCharactersUc(usecase: GetAllCharactersUc) : @JvmSuppressWildcards CharacterDomainLayerContract.PresentationLayer.UseCase<Characters> = usecase

    @Provides
    @Named("get_characters_next_page")
    fun providesGetCharactersNextPageUc(getNexPageUc: GetCharacterNextPageUc) : @JvmSuppressWildcards CharacterDomainLayerContract.PresentationLayer.UseCase<Characters> = getNexPageUc

    @Provides
    @Named("get_characters_by_name")
    fun providesGetCharactersByNameUc(getByName: GetCharactersByNameUC) : GetCharactersByNameUC = getByName

    @Provides
    @Named("get_characters_by_status")
    fun providesGetCharactersByStatusUc(getByStatus: GetCharactersByStatusUc) : GetCharactersByStatusUc = getByStatus

    @Provides
    @Named("get_characters_by_gender")
    fun providesGetCharactersByGenderUc(getByGender: GetCharactersByGenderUc) : GetCharactersByGenderUc = getByGender

    @Provides
    @Named("get_characters_by_gender_and_status")
    fun providesGetCharactersByStatusAndGenderUc(getByGenderAndStatus: GetCharactersByStatusAndGenderUc) : GetCharactersByStatusAndGenderUc = getByGenderAndStatus

}