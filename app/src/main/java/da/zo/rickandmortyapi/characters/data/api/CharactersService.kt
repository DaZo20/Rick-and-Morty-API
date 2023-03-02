package da.zo.rickandmortyapi.characters.data.api

import da.zo.rickandmortyapi.characters.data.model.CharactersDto
import da.zo.rickandmortyapi.characters.domain.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//
// Created by DaZo20 on 10/01/2023.
//

interface CharactersService {

    @GET("character/")
    suspend fun getAllCharactersList(@Query("page") page: Int = 1): Response<CharactersDto?>

    @GET("character/")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharactersDto?>

    @GET("character/")
    suspend fun getCharactersByStatusAndGender(
        @Query("status") status: String,
        @Query("gender") gender: String,
        @Query("page") page: Int = 1): Response<CharactersDto?>

    @GET("character/")
    suspend fun getCharactersByStatus(
        @Query("status") status: String,
        @Query("page") page: Int = 1): Response<CharactersDto?>

    @GET("character/")
    suspend fun getCharactersByGender(
        @Query("gender") gender: String,
        @Query("page") page: Int = 1): Response<CharactersDto?>



}