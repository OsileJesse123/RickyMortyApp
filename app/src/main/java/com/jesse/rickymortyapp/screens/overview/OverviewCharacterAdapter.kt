package com.jesse.rickymortyapp.screens.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesse.rickymortyapp.databinding.CharacterItemBinding
import com.jesse.rickymortyapp.network.RickMortyCharacter

class OverviewCharacterAdapter
    : ListAdapter<RickMortyCharacter,
        OverviewCharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    class CharacterViewHolder(private val binding: CharacterItemBinding):
        RecyclerView.ViewHolder(binding.root){
        private var xter: RickMortyCharacter? = null
        init{
            binding.root.setOnClickListener{
                xter?.let{
                    onCharacterItemClicked?.let { listener -> listener(it) }
                }
            }
        }
        fun bind(character: RickMortyCharacter){
            xter = character
            binding.rickMortyCharacter = character
            binding.executePendingBindings()
        }

        companion object {
            var onCharacterItemClicked: ((character: RickMortyCharacter) -> Unit)? = null
            fun from(parent: ViewGroup): CharacterViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val characterBinding = CharacterItemBinding.inflate(layoutInflater,
                    parent, false)
                return CharacterViewHolder(characterBinding)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int
    ) {
        val character = getItem(position)
        holder.bind(character)
    }
}

class CharacterDiffCallback: DiffUtil.ItemCallback<RickMortyCharacter>(){
    override fun areItemsTheSame(
        oldItem: RickMortyCharacter,
        newItem: RickMortyCharacter
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RickMortyCharacter,
        newItem: RickMortyCharacter
    ): Boolean {
        return oldItem == newItem
    }
}