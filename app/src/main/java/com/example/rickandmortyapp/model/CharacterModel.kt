package com.example.rickandmortyapp.model

import android.location.Location
import com.example.rickandmortyapp.dto.Result
import com.google.gson.annotations.SerializedName

data class CharacterModel(

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: ArrayList<String>,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    val location: LocationModel,
    val origin: OriginModel,
)
data class LocationModel(
    val name: String,
    val url: String
)

data class OriginModel(
    val name: String,
    val url: String
)



data class CharacterResponse(val results : ArrayList<CharacterModel>)
