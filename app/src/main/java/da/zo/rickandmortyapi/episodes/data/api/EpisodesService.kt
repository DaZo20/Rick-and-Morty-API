package da.zo.rickandmortyapi.episodes.data.api


import da.zo.rickandmortyapi.episodes.data.model.EpisodesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
interface EpisodesService {
    @GET("episode")
    suspend fun getAllEpisodesList(@Query("page") page: Int = 1): Response<EpisodesDto?>

}