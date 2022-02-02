package com.jesse.rickymortyapp.utils

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jesse.rickymortyapp.R
import com.jesse.rickymortyapp.network.RickMortyCharacter
import com.jesse.rickymortyapp.screens.detail.CharacterDetailAdapter
import com.jesse.rickymortyapp.screens.overview.OverviewCharacterAdapter
import com.jesse.rickymortyapp.screens.overview.RickMortyState

@BindingAdapter("imageSrcUrl")
fun bindImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        val imageUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image))
            .into(imageView)
    }
}

@BindingAdapter("lifeStatusSrc")
fun bindLifeStatusImage(imageView: ImageView, status: String?){
    status?.let{
        when(it){
            "Alive" -> imageView.setImageResource(R.drawable.green_dot)
            "Dead" -> imageView.setImageResource(R.drawable.red_dot)
        }
    }
}

@BindingAdapter("recyclerData")
fun setRecyclerData(recyclerView: RecyclerView, characters: List<RickMortyCharacter>?){
    val adapter = recyclerView.adapter as OverviewCharacterAdapter
    adapter.submitList(characters)
}

@BindingAdapter("formatText")
fun formatTextViewText(textView: TextView, name: String?){
    name?.let{
        var newName = ""
        var count = 0
        if(it.length > 16) {
            for(letter in it){
                newName+=letter
                count++
                if (count == 16) break
            }
            newName += "..."
            textView.text = newName
        } else {
            textView.text = it
        }
    }
}

@BindingAdapter("networkStatus")
fun bindStatus(imageView: ImageView, rickMortyState: RickMortyState?){
    rickMortyState?.let{
        when(it){
            RickMortyState.LOADING -> {
                imageView.setImageResource(R.drawable.loading_img)
                imageView.visibility = VISIBLE
            }
            RickMortyState.SUCCESSFUL -> {
                imageView.visibility = GONE
            }
            RickMortyState.ERROR -> {
                imageView.setImageResource(R.drawable.ic_connection_error)
                imageView.visibility = VISIBLE
            }
        }
    }
}

@BindingAdapter("characterDetails")
fun bindCharacterDetails(recyclerView: RecyclerView, details: List<String>?){
    details?.let{
        val adapter = recyclerView.adapter as CharacterDetailAdapter
        adapter.details = details
    }
}