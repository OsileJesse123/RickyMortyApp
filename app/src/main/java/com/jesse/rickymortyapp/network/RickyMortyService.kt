package com.jesse.rickymortyapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val  BASE_URL = "https://rickandmortyapi.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//MoshiConverterFactory.create(moshi)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RickyMortyService {
    @GET("character")
    suspend fun getCharacters(): RickMortyObject

    @GET("character")
    suspend fun getCharactersByStatus(@Query("status") type:String): RickMortyObject
}

object RickyMortyApi {
    val retrofitService: RickyMortyService by lazy {
        retrofit.create(RickyMortyService::class.java)
    }
}