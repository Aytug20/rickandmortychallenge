package com.example.rickandmortyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.LocationItemBinding
import com.example.rickandmortyapp.dto.Location
import com.example.rickandmortyapp.dto.Result

class LocationRecyclerViewAdapter(
    private var locationList: ArrayList<Result>,
    private val listener: Listener
) : RecyclerView.Adapter<LocationRecyclerViewAdapter.ItemHolder>(), Filterable {

    fun submitList(list: ArrayList<Result>) {
        this.locationList = list
        notifyDataSetChanged()
    }

    interface Listener {
        fun onItemClick(result: Result,checked: Boolean)
    }

    inner class ItemHolder(var binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Result) {
            binding.textLocation.text = model.name
            binding.textLocation.textOn = model.name
            binding.textLocation.textOff = model.name
            binding.textLocation.setOnCheckedChangeListener { compoundButton, b ->
                listener.onItemClick(result = model,b)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding =
            LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int {
        return locationList.count()
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


}