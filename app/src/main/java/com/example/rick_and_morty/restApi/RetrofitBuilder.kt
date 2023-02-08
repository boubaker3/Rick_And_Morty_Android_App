package com.example.rick_and_morty.restApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    object CharacterApiSingleton
    {
        val rickAndMortyApi:RickAndMortyApi by lazy {
                Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RickAndMortyApi::class.java);
        }

    }


}