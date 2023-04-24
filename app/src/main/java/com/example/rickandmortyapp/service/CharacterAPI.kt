package com.example.rickandmortyapp.service

import com.example.rickandmortyapp.model.CharacterModel
import com.example.rickandmortyapp.model.CharacterResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterAPI {
    @GET("character")
    fun getCharacterList(): Call<CharacterResponse>

    @GET("character/{ids}")
    fun getCharacterListById(
        @Path("ids") ids: String
    ): Call<List<CharacterModel>>
}