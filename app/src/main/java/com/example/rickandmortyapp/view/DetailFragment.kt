package com.example.rickandmortyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RetrofitClient
import com.example.rickandmortyapp.adapter.CharacterRecyclerViewAdapter
import com.example.rickandmortyapp.databinding.FragmentDetailBinding
import com.example.rickandmortyapp.databinding.FragmentSplashBinding
import com.example.rickandmortyapp.model.CharacterModel
import com.example.rickandmortyapp.model.CharacterResponse
import com.example.rickandmortyapp.model.EpisodeModel
import com.example.rickandmortyapp.model.Result
import com.example.rickandmortyapp.service.CharacterAPI
import com.example.rickandmortyapp.service.EpisodeAPI
import com.example.rickandmortyapp.util.downloadImage
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var characterModel:CharacterModel? = null
    private var episodeModel:Result? = null
    private var characterModels: ArrayList<CharacterModel> = arrayListOf()
    private var episodeModels: ArrayList<Result> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        val callback= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var episodes = ""
        val jsonDetail = arguments?.getString("character")
        if (jsonDetail != null) {
            characterModel = Gson().fromJson(jsonDetail,CharacterModel::class.java)
            characterModel?.let {
                binding.characterName.setText(it.name)
                binding.characterImage.downloadImage(it.image)
                println("Species: ${binding.characterSpecy.setText(it.species)}")
                binding.characterStatus.setText(it.status)
                binding.characterGender.setText(it.gender)
                binding.characterOrigin.setText(it.origin.name)
                binding.characterLocation.setText(it.location.name)
                binding.characterCreated.text = setDate(it.created)
                for (i in it.episode)
                    episodes += i.split("/")[5] + ","
                binding.characterEpisodes.setText(episodes)
                binding.toggleBack.setOnClickListener {
                    findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                }

            }


        }
    }




    private fun setDate(date: String): String {
        if (!date.isNullOrEmpty()) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateF: Date = inputFormat.parse(date)!!
            return outputFormat.format(dateF)
        } else {
            return ""
        }
    }


}