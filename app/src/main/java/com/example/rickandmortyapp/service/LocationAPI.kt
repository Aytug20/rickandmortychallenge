package com.example.rickandmortyapp.service

import com.example.rickandmortyapp.dto.Location
import com.example.rickandmortyapp.model.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationAPI {
    @GET("location")
    fun getLocationList(@Query("page") page: Int): Call<Location>

    @GET("location")
    fun getLocationListName(@Query("name") name: String,
                            @Query("page") page: Int): Call<CharacterResponse>


}