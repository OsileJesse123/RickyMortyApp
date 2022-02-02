package com.jesse.rickymortyapp.screens.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.rickymortyapp.network.RickMortyCharacter
import com.jesse.rickymortyapp.network.RickyMortyService
import kotlinx.coroutines.launch

enum class RickMortyState{LOADING, ERROR, SUCCESSFUL}

class OverviewViewModel(private val retrofitService: RickyMortyService): ViewModel() {

    private val _characters: MutableLiveData<List<RickMortyCharacter>> = MutableLiveData()
    val characters: LiveData<List<RickMortyCharacter>> get() = _characters

    private val _state:MutableLiveData<RickMortyState> = MutableLiveData()
    val state: LiveData<RickMortyState> get() = _state
    var currentItemPosition = 0

    init{
        setCharacters("All")
    }

    fun setCharacters(status: String){
        _state.value = RickMortyState.LOADING
        viewModelScope.launch{
            try{
                _characters.value = when(status){
                   "All" ->  retrofitService.getCharacters().results
                   "Alive" -> retrofitService.getCharactersByStatus(status).results
                    "Dead" -> retrofitService.getCharactersByStatus(status).results
                    else -> listOf()
               }
                _state.value = RickMortyState.SUCCESSFUL
            } catch(e: Exception){
                _state.value = RickMortyState.ERROR
                _characters.value = listOf()
            }
        }
    }

}