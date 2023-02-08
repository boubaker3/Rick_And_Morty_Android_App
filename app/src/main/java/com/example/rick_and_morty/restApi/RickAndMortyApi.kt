package com.example.rick_and_morty.restApi

import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.model.RickAndMortyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page:Int,@Query("per_page") perPage:Int): RickAndMortyResponse
    @GET("character")
    suspend fun getCharacter(@Query("page") page:Int, @Query("per_page") perPage:Int, @Query("name") search:String): RickAndMortyResponse

}