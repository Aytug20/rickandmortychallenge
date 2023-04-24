package com.example.rickandmortyapp.service

import com.example.rickandmortyapp.model.CharacterResponse
import com.example.rickandmortyapp.model.EpisodeModel
import retrofit2.Call
import retrofit2.http.GET

interface EpisodeAPI {

    @GET("episode")
    fun getEpisodeList(): Call<EpisodeModel>
}