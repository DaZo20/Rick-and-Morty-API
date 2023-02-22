package da.zo.rickandmortyapi.common.utils

import retrofit2.Converter
import retrofit2.Retrofit

//
// Created by DaZo20 on 13/01/2023.
//
private const val DEFAULT_BASE_URL: String = "https://rickandmortyapi.com/api/"

fun getRetrofitInstance(baseUrl: String = DEFAULT_BASE_URL, converterFactory: Converter.Factory) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(converterFactory)
    .build()