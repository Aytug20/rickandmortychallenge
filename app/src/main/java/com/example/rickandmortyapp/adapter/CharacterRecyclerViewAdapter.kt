package com.example.rickandmortyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.RowLayoutBinding
import com.example.rickandmortyapp.model.CharacterModel
import com.example.rickandmortyapp.util.downloadImage

class CharacterRecyclerViewAdapter(
    private var characterList: ArrayList<CharacterModel>,
    private val listener: Listener
) : RecyclerView.Adapter<CharacterRecyclerViewAdapter.RowHolder>() {
    fun submitList(list: ArrayList<CharacterModel>) {
        this.characterList = list
        notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClick(characterModel: CharacterModel)
    }

    inner class RowHolder(var binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CharacterModel) {
            binding.textName.text = model.name
            binding.textGender.text = model.gender
            binding.imageView.downloadImage(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(characterList[position])
        }
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int {
        return characterList.count()
    }

}