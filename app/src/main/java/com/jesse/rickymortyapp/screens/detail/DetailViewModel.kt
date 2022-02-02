package com.jesse.rickymortyapp.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesse.rickymortyapp.R
import com.jesse.rickymortyapp.network.RickMortyCharacter

class DetailViewModel(private val application_: Application,
                      private val character: RickMortyCharacter
): AndroidViewModel(application_) {

    private var _characterDetails: MutableLiveData<List<String>> = MutableLiveData()
    val characterDetails: LiveData<List<String>> get() = _characterDetails

    init{
        setCharacterDetails()
    }
    private fun setCharacterDetails(){
        _characterDetails.value = formatAndCreateCharacterDetailsList()
    }
    private fun formatAndCreateCharacterDetailsList(): MutableList<String>{

        val details = mutableListOf<String>()

        val name = application_.applicationContext
            .getString(R.string.character_name, character.name)
        val status = application_.applicationContext
            .getString(R.string.character_status, character.status)
        val species = application_.applicationContext
            .getString(R.string.character_species, character.species)
        val location = application_.applicationContext
            .getString(R.string.character_location, character.location.name)
        val origin = application_.applicationContext
            .getString(R.string.character_origin, character.origin.name)
        val gender = application_.applicationContext
            .getString(R.string.character_gender, character.gender)
        val type = if(character.type.isEmpty()) application_.applicationContext
            .getString(R.string.non)
        else application_.applicationContext
            .getString(R.string.character_type, character.type)

        details.add(name)
        details.add(status)
        details.add(species)
        details.add(location)
        details.add(origin)
        details.add(gender)
        details.add(type)

        return details
    }
}