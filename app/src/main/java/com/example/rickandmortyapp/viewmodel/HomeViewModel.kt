package com.example.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapp.RetrofitClient
import com.example.rickandmortyapp.dto.Location
import com.example.rickandmortyapp.dto.Result
import com.example.rickandmortyapp.model.CharacterModel
import com.example.rickandmortyapp.model.CharacterResponse
import com.example.rickandmortyapp.service.CharacterAPI
import com.example.rickandmortyapp.service.LocationAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeViewModel {

    private var page = 1

    private val checkedLocations = mutableListOf<String>()
    private val pressedLocations = mutableListOf<String>()

    val locations: MutableLiveData<ArrayList<Result>> by lazy {
        MutableLiveData<ArrayList<Result>>()
    }

    /**
     * view tarafına aktarılan data
     * tüm liste veya filtrelenen liste
     * [loadCharacters] veya [loadCharactersById] üzerinden geliyor
     */
    val characters: MutableLiveData<ArrayList<CharacterModel>> by lazy {
        MutableLiveData<ArrayList<CharacterModel>>()
    }

    /**
     * tüm karakterler
     * [loadCharacters] üzerinden geliyor
     * filtreleme bu listeden yapılıyor
     */
    private val _characterList = arrayListOf<CharacterModel>()

    private val retrofitInstance: Retrofit by lazy {
        RetrofitClient.getRetrofitInstance()
    }

    fun loadDataLocation() {
        with(retrofitInstance.create(LocationAPI::class.java)) {
            this.getLocationList(page).enqueue(object : Callback<Location> {

                override fun onResponse(call: Call<Location>, response: Response<Location>) {
                    if (response.isSuccessful) {

                        response.body()?.let { it ->
                            locations.postValue(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<Location>, t: Throwable) {

                }
            })
        }
    }

    /**
     * tüm karakterlerin alındığı yer
     */
    fun loadCharacters() {
        with(retrofitInstance.create(CharacterAPI::class.java)) {
            this.getCharacterList().enqueue(object : Callback<CharacterResponse> {

                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            _characterList.addAll(it.results)
                            characters.postValue(it.results)
                        }
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    /**
     * seçilen lokasyona göre alınan karakterler
     * @param result : seçilen karakter
     * @param checked : seçilip seçilmeme durumu
     */
    fun loadCharactersById(result: Result, checked: Boolean) {

        if (checked)
            pressedLocations.add(result.id.toString())
        else
            pressedLocations.remove(result.id.toString())

        _characterList.forEach { character ->
            if (character.location.name == result.name)
                if (checked)
                    checkedLocations.add(character.id.toString())
                else
                    checkedLocations.remove(character.id.toString())
        }

        val ids = StringBuilder()
        checkedLocations.onEach {
            ids.append(it)
            ids.append(",")
        }
        val idsString = ids.toString()

        if (checkedLocations.isNotEmpty()) {
            with(retrofitInstance.create(CharacterAPI::class.java)) {
                this.getCharacterListById(idsString)
                    .enqueue(object : Callback<List<CharacterModel>> {

                        override fun onResponse(
                            call: Call<List<CharacterModel>>,
                            response: Response<List<CharacterModel>>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.let { it ->
                                    characters.postValue(it as ArrayList<CharacterModel>)
                                }
                            }
                        }

                        override fun onFailure(call: Call<List<CharacterModel>>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
        } else if (pressedLocations.isNotEmpty()) {
            characters.postValue(arrayListOf())
        } else {
            loadCharacters()
        }
    }
}