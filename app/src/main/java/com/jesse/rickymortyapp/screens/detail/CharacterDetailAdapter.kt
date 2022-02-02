package com.jesse.rickymortyapp.screens.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesse.rickymortyapp.databinding.CharacterDetailItemBinding

class CharacterDetailAdapter:
    RecyclerView.Adapter<CharacterDetailAdapter.CharacterDetailViewHolder>() {

    var details = listOf<String>()

    class CharacterDetailViewHolder(val binding: CharacterDetailItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(detail: String) {
            binding.characterDetail = detail
            binding.executePendingBindings()
        }

        companion object{
                fun from(parent: ViewGroup):CharacterDetailViewHolder{
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val cDBinding = CharacterDetailItemBinding.inflate(layoutInflater)
                    return CharacterDetailViewHolder(cDBinding)
                }
            }
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterDetailViewHolder {
        return CharacterDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: CharacterDetailViewHolder,
        position: Int
    ) {
        holder.bind(details[position])
    }

    override fun getItemCount(): Int = details.size
}