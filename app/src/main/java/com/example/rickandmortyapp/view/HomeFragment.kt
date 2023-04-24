package com.example.rickandmortyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.adapter.CharacterRecyclerViewAdapter
import com.example.rickandmortyapp.adapter.LocationRecyclerViewAdapter
import com.example.rickandmortyapp.databinding.FragmentHomeBinding
import com.example.rickandmortyapp.dto.Result
import com.example.rickandmortyapp.model.CharacterModel
import com.example.rickandmortyapp.viewmodel.HomeViewModel
import com.google.gson.Gson
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var characterRecyclerViewAdapter: CharacterRecyclerViewAdapter? = null
    private var locationRecyclerViewAdapter: LocationRecyclerViewAdapter? = null

    private val homeViewModel: HomeViewModel by lazy {
        HomeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initObservers()
        loadData()

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            binding.recyclerViewLocation.setOnClickListener {
                findNavController().popBackStack(R.id.recyclerView, false)
            }

        }
    }

    private fun initObservers() {
        with((viewLifecycleOwner)) {
            homeViewModel.locations.observe(this) { it ->
                it.let {
                    locationRecyclerViewAdapter = LocationRecyclerViewAdapter(
                        it as ArrayList<Result>,
                        object : LocationRecyclerViewAdapter.Listener {
                            override fun onItemClick(result: Result, checked: Boolean) {
                                homeViewModel.loadCharactersById(result, checked)
                            }
                        })
                }
                binding.recyclerViewLocation.adapter = locationRecyclerViewAdapter
            }

            homeViewModel.characters.observe(this) { it ->
                if (characterRecyclerViewAdapter == null) {
                    it.let { characters ->
                        characterRecyclerViewAdapter = CharacterRecyclerViewAdapter(
                            characters as ArrayList<CharacterModel>,
                            object : CharacterRecyclerViewAdapter.Listener {
                                override fun onItemClick(characterModel: CharacterModel) {
                                    Toast.makeText(
                                        requireContext(),
                                        "character - ${characterModel.name}",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    val bundle = Bundle()
                                    bundle.putString("character", Gson().toJson(characterModel))
                                    findNavController().navigate(
                                        R.id.action_homeFragment_to_detailFragment,
                                        bundle
                                    )
                                }

                            }
                        )
                    }
                    binding.recyclerView.adapter = characterRecyclerViewAdapter
                }else{
                    characterRecyclerViewAdapter!!.submitList(it)
                }
            }
        }
    }

    private fun loadData() {
        homeViewModel.loadCharacters()
        homeViewModel.loadDataLocation()
    }
}


